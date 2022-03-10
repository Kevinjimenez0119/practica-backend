package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.dominio.modelo.ClienteFile;
import com.pragma.customer.dominio.modelo.FileImagen;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.modelo.Cliente;
import com.pragma.customer.infraestructura.mappers.ClienteInterfaceMapper;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.ClienteInterfaceRepository;
import com.pragma.customer.infraestructura.persistencia.repository.TipoDocumentoInterfaceReporsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteInterfaceService {

    Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteInterfaceRepository clienteInterfaceRepository;

    @Autowired
    private TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Autowired
    private ClienteInterfaceMapper clienteInterfaceMapper;

    @Autowired
    private FileImagenServiceImpl fileImagenService;

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
            Cliente clienteUpdate = findByIdentificacion(cliente.getIdentificacion());
            clienteUpdate.setNombres(cliente.getNombres());
            clienteUpdate.setApellidos(cliente.getApellidos());
            clienteUpdate.setCiudadNacimiento(cliente.getCiudadNacimiento());
            clienteUpdate.setEdad(cliente.getEdad());
            clienteUpdate.setTipoDocumento(cliente.getTipoDocumento());
            clienteUpdate.setFechaNacimiento(cliente.getFechaNacimiento());
            clienteUpdate.setIdentificacion((cliente.getIdentificacion()));
            save(clienteUpdate);
        } catch (Exception e) {
            logger.error("Error al registrar el cliente", e);
        }
    }

    @Override
    public void delete(Integer identificacion) {
        try {
            Optional<ClienteEntidad> clienteEntidad = clienteInterfaceRepository.findById(identificacion);
            fileImagenService.delete(identificacion);
            clienteInterfaceRepository.delete(clienteEntidad.get());
        } catch (Exception e) {
            logger.error("Error al eliminar el cliente", e);
        }
    }

    @Override
    public List<Cliente> findAll() {
        try {
            return clienteInterfaceMapper.toClienteListDto(clienteInterfaceRepository.findAll());
        } catch (Exception e) {
            logger.error("Error al listar clientes", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Cliente findById(Integer id) {
        try {
            return clienteInterfaceMapper.toClienteDto(clienteInterfaceRepository.findById(id).get());
        } catch (Exception e) {
            logger.error("Error al buscar cliente por id", e);
        }
        return null;
    }

    @Override
    public ClienteFile findByIdentificacionFile(Integer identificacion) {
        try {
            Cliente cliente = clienteInterfaceMapper.toClienteDto(clienteInterfaceRepository.findByIdentificacion(identificacion).get());
            FileImagen fileImagen = fileImagenService.findByNumeroIdentificacion(identificacion);
            if(fileImagen == null) {
                fileImagen = FileImagen.builder()
                        .fileName("")
                        .fileType("")
                        .base64("")
                        .identificacion(cliente.getIdentificacion()).build();
                return maptoFotocliente(cliente, fileImagen);
            }
            return maptoFotocliente(cliente, fileImagen);
        } catch (Exception e) {
            logger.error("Error al buscar cliente por id con imagen", e);
        }
        return null;
    }

    private ClienteFile maptoFotocliente(Cliente cliente, FileImagen fileImagen)
    {
        ClienteFile clienteFile = ClienteFile.builder()
                .id(cliente.getId())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .identificacion(cliente.getIdentificacion())
                .tipoDocumento(cliente.getTipoDocumento())
                .edad(cliente.getEdad())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .ciudadNacimiento(cliente.getCiudadNacimiento())
                .fileImagen(fileImagen).build();
        return clienteFile;
    }

    @Override
    public Cliente findByIdentificacion(Integer identificacion) {
        try {
            return clienteInterfaceMapper.toClienteDto(clienteInterfaceRepository.findByIdentificacion(identificacion).get());
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
    public List<Cliente> findByAge(Integer edad) {
        try {
            return clienteInterfaceMapper.toClienteListDto(clienteInterfaceRepository.findByAge(edad));
        } catch (Exception e) {
            logger.error("Error al listar cliente por edad", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Page<Cliente> findAllPag(Pageable pageable) {
        try {
            Page<ClienteEntidad> clienteEntidadList = clienteInterfaceRepository.findAll(pageable);
            List<Cliente> clienteList = clienteInterfaceMapper.toClienteListDto(clienteEntidadList.toList());
            Page<Cliente> clientePageList = new PageImpl<>(clienteList);
            return clientePageList;
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
