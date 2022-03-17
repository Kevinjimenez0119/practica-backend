package com.pragma.customer.dominio.useCase.cliente;

import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import com.pragma.customer.infraestructura.persistencia.service.impl.ClienteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

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

    public boolean guardar(ClienteDto cliente) throws Exception {
        return clienteInterfaceService.save(cliente);
    }

    public boolean actualizar(ClienteDto cliente) throws Exception {
        return clienteInterfaceService.update(cliente);
    }

    public boolean eliminar(Integer id) throws Exception {
        return clienteInterfaceService.delete(id);
    }

    public List<ClienteDto> listar() throws Exception {
        return clienteInterfaceService.findAll();
    }

    public ClienteDto buscarPorIdentificacion(Integer identificacion) throws Exception {
        return clienteInterfaceService.findByIdentificacion(identificacion);
    }

    public ClienteFileDto buscarPorIdentificacionFile(Integer identificacion) throws Exception {
        return clienteInterfaceService.findByIdentificacionFile(identificacion);
    }

    public List<ClienteDto> listarPorEdadMayor(Integer edad) throws Exception {
        return clienteInterfaceService.findByAge(edad);
    }

    public Page<ClienteDto> listarPag(Pageable pageable) throws Exception {
        return clienteInterfaceService.findAllPag(pageable);
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
