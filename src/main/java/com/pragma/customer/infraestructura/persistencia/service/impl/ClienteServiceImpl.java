package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.modelo.Cliente;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.ClienteInterfaceRepository;
import com.pragma.customer.infraestructura.persistencia.repository.TipoDocumentoInterfaceReporsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteInterfaceService {

    Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteInterfaceRepository clienteInterfaceRepository;

    @Autowired
    private TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Override
    public void save(Cliente cliente) {
        try {
            Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(cliente.getTipoDocumento());
            ClienteEntidad clienteEntidad = ClienteEntidad.builder()
                    .nombres(cliente.getNombres())
                    .apellidos(cliente.getApellidos())
                    .ciudadNacimiento(cliente.getCiudadNacimiento())
                    .tipoDocumentoEntidad(tipoDocumentoEntidad.get())
                    .edad(cliente.getEdad())
                    .fechaNacimiento(cliente.getFechaNacimiento())
                    .identificacion(cliente.getIdentificacion())
                            .build();
            clienteInterfaceRepository.save(clienteEntidad);
        } catch (Exception e) {
            logger.error("Error al registrar el cliente", e);
        }
    }

    @Override
    public void update(Cliente cliente) {
        try {
            Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(cliente.getTipoDocumento());
            Optional<ClienteEntidad> clienteEntidad = findByIdentificacion(cliente.getIdentificacion());
            clienteEntidad.get().setNombres(cliente.getNombres());
            clienteEntidad.get().setApellidos(cliente.getApellidos());
            clienteEntidad.get().setCiudadNacimiento(cliente.getCiudadNacimiento());
            clienteEntidad.get().setEdad(cliente.getEdad());
            clienteEntidad.get().setTipoDocumentoEntidad(tipoDocumentoEntidad.get());
            clienteEntidad.get().setFechaNacimiento(cliente.getFechaNacimiento());
            clienteEntidad.get().setIdentificacion((cliente.getIdentificacion()));
            clienteInterfaceRepository.save(clienteEntidad.get());
        } catch (Exception e) {
            logger.error("Error al registrar el cliente", e);
        }
    }

    @Override
    public void delete(Integer identificacion) {
        try {
            Optional<ClienteEntidad> clienteEntidad = findById(identificacion);
            clienteInterfaceRepository.delete(clienteEntidad.get());
        } catch (Exception e) {
            logger.error("Error al eliminar el cliente", e);
        }
    }

    @Override
    public List<ClienteEntidad> findAll() {
        try {
            return (List<ClienteEntidad>) clienteInterfaceRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al listar clientes", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<ClienteEntidad> findById(Integer id) {
        try {
            return clienteInterfaceRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error al buscar cliente por id", e);
        }
        return null;
    }

    @Override
    public Optional<ClienteEntidad> findByIdentificacion(Integer identificacion) {
        try {
            return clienteInterfaceRepository.findByIdentificacion(identificacion);
        } catch (Exception e) {
            logger.error("Error al buscar cliente por identificacion", e);
        }
        return null;
    }

    @Override
    public boolean existsByIdentificacion(Integer identificacion) {
        try {
            return clienteInterfaceRepository.existsByIdentificacion(identificacion);
        } catch (Exception e) {
            logger.error("Error al buscar cliente exist", e);
        }
        return false;
    }

    @Override
    public List<ClienteEntidad> findByAge(Integer edad) {
        try {
            return (List<ClienteEntidad>) clienteInterfaceRepository.findByAge(edad);
        } catch (Exception e) {
            logger.error("Error al listar cliente por edad", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Page<ClienteEntidad> findAllPag(Pageable pageable) {
        try {
            return clienteInterfaceRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error al listar cliente por edad", e);
        }
        return null;
    }

    @Override
    public boolean validateClient(Cliente cliente) {
        return false;
    }

    @Override
    public Integer getAgeByDate(Date fechaNacimiento) {
        try {
            java.sql.Date bDate = new java.sql.Date(fechaNacimiento.getTime());
            LocalDate fechaNac = bDate.toLocalDate();
            LocalDate ahora = LocalDate.now();

            Period periodo = Period.between(fechaNac, ahora);

            return periodo.getYears();
        } catch (Exception e) {
            logger.error("Error al calcular edad", e);
        }
        return null;
    }

    @Override
    public LocalDate getBirthdayDate(Date fechaNacimiento) {
        try {
            /*Fecha actual*/
            LocalDate today = LocalDate.now();


            /*Convertir Date a LocalDate*/
            java.sql.Date bDate = new java.sql.Date(fechaNacimiento.getTime());
            LocalDate fechaNac = bDate.toLocalDate();
            LocalDate nextBDay = fechaNac.withYear(today.getYear());

            /*Si el cumpleaños ya ocurrió este año, agrega 1 año*/
            if (nextBDay.isBefore(today) || nextBDay.isEqual(today)) {
                nextBDay = nextBDay.plusYears(1);
            }

            Period p = Period.between(today, nextBDay);
            long totalDias = ChronoUnit.DAYS.between(today, nextBDay);

            /*Cuando totalDias=365 hoy es el cumpleaños*/

            if (totalDias == 365) {

                return today;

            } else {

                today = today.plusMonths(p.getMonths());
                return today.plusDays(p.getDays());
            }

        }catch (DateTimeParseException e) {
            logger.error("Error al calcular la fecha", e);
        }
        return null;
    }
}
