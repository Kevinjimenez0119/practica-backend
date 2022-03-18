package com.pragma.customer.dominio.useCase.cliente;

import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import com.pragma.customer.infraestructura.exceptions.RequestException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ClienteUseCase {

    Logger logger = LoggerFactory.getLogger(ClienteUseCase.class);

    private final ClienteInterfaceService clienteInterfaceService;

    private final TipoDocumentoUseCase tipoDocumentoUseCase;

    public boolean guardar(ClienteDto cliente) throws Exception {
        if(!clienteInterfaceService.existsByIdentificacion(cliente.getIdentificacion())) {
            tipoDocumentoUseCase.existsByTipoDocumento(cliente.getTipoDocumento());
            return clienteInterfaceService.save(cliente);
        } else {
            throw new RequestException(400, ErrorsUtils.identificacionYaRegistrada(cliente.getIdentificacion().toString()));
        }
    }

    public boolean actualizar(ClienteDto cliente) throws Exception {
        existsByIdentificacion(cliente.getIdentificacion());
        tipoDocumentoUseCase.existsByTipoDocumento(cliente.getTipoDocumento());
        return clienteInterfaceService.update(cliente);
    }

    public boolean eliminar(Integer id) throws Exception {
        existsByIdentificacion(id);
        return clienteInterfaceService.delete(id);
    }

    public List<ClienteDto> listar() throws Exception {
        List<ClienteDto> clienteDtoList = clienteInterfaceService.findAll();
        if(clienteDtoList.isEmpty())
        {
            throw new LogicException(204, ErrorsUtils.sinRegistros());
        }
        return clienteDtoList;
    }

    public ClienteDto buscarPorIdentificacion(Integer identificacion) throws Exception {
        existsByIdentificacion(identificacion);
        return clienteInterfaceService.findByIdentificacion(identificacion);
    }

    public ClienteFileDto buscarPorIdentificacionFile(Integer identificacion) throws Exception {
        existsByIdentificacion(identificacion);
        return clienteInterfaceService.findByIdentificacionFile(identificacion);
    }

    public List<ClienteDto> listarPorEdadMayor(Integer edad) throws Exception {
        List<ClienteDto> clienteDtoList = clienteInterfaceService.findByAge(edad);
        if(clienteDtoList.isEmpty())
        {
            throw new LogicException(204, ErrorsUtils.sinRegistros());
        }
        return clienteDtoList;
    }

    public Page<ClienteDto> listarPag(Pageable pageable) throws Exception {
        return clienteInterfaceService.findAllPag(pageable);
    }

    public boolean existsByTipoDocumentoEntidad(String tipo) throws Exception {
        return clienteInterfaceService.existsByTipoDocumentoEntidad(tipo);
    }

    public boolean existsByIdentificacion(Integer identificacion) throws Exception {
        if(clienteInterfaceService.existsByIdentificacion(identificacion)) {
            return true;
        } else {
            throw new RequestException(404, ErrorsUtils.identificacionNoRegistrada(identificacion.toString()));
        }
    }

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
