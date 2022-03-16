package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.modelo.FileImagenDto;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.service.FileImagenServiceClient;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import com.pragma.customer.infraestructura.exceptions.RequestException;
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
import org.springframework.http.HttpStatus;
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
    private TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    @Autowired
    private ClienteInterfaceMapper clienteInterfaceMapper;

    @Autowired
    private FileImagenServiceClient fileImagenServiceClient;

    @Override
    public void save(ClienteDto cliente) throws Exception {
        validateClient(cliente);
        if(!clienteInterfaceRepository.existsByIdentificacion(cliente.getIdentificacion())) {
            if (tipoDocumentoInterfaceService.existsByTipoDocumento(cliente.getTipoDocumento())) {
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
            }
        } else {
            throw new RequestException("code", HttpStatus.BAD_REQUEST, ErrorsUtils.identificacionYaRegistrada(cliente.getIdentificacion().toString()));
        }
    }

    @Override
    public void update(ClienteDto cliente) throws Exception{
        if(existsByIdentificacion(cliente.getIdentificacion())) {
            if(tipoDocumentoInterfaceService.existsByTipoDocumento(cliente.getTipoDocumento())) {
                TipoDocumentoEntidad tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(cliente.getTipoDocumento()).get();
                ClienteEntidad clienteUpdate = clienteInterfaceRepository.findByIdentificacion(cliente.getIdentificacion()).get();
                clienteUpdate.setNombres(cliente.getNombres());
                clienteUpdate.setApellidos(cliente.getApellidos());
                clienteUpdate.setCiudadNacimiento(cliente.getCiudadNacimiento());
                clienteUpdate.setEdad(cliente.getEdad());
                clienteUpdate.setTipoDocumentoEntidad(tipoDocumentoEntidad);
                clienteUpdate.setFechaNacimiento(cliente.getFechaNacimiento());
                clienteUpdate.setIdentificacion((cliente.getIdentificacion()));
                clienteInterfaceRepository.save(clienteUpdate);
            }
        }
    }

    @Override
    public void delete(Integer identificacion) throws Exception{
        if(existsByIdentificacion(identificacion)) {
            FileImagenDto fileImagenDto = fileImagenServiceClient.findByNumeroIdentificacion(identificacion);
            if(fileImagenDto == null) {
                Optional<ClienteEntidad> clienteEntidad = clienteInterfaceRepository.findByIdentificacion(identificacion);
                clienteInterfaceRepository.delete(clienteEntidad.get());
            } else {
                if(fileImagenServiceClient.delete(identificacion)==true) {
                    Optional<ClienteEntidad> clienteEntidad = clienteInterfaceRepository.findByIdentificacion(identificacion);
                    clienteInterfaceRepository.delete(clienteEntidad.get());
                }
            }
        }
    }

    @Override
    public List<ClienteDto> findAll() throws Exception{
            List<ClienteDto> clienteDtoList = clienteInterfaceMapper.toClienteListDto(clienteInterfaceRepository.findAll());
            if(clienteDtoList.isEmpty())
            {
                throw new LogicException("code", HttpStatus.NO_CONTENT, ErrorsUtils.sinRegistros());
            }
            return clienteDtoList;
    }

    @Override
    public ClienteDto findById(Integer id) {
        try {
            return clienteInterfaceMapper.toClienteDto(clienteInterfaceRepository.findById(id).get());
        } catch (Exception e) {
            logger.error("Error al buscar cliente por id", e);
        }
        return null;
    }

    @Override
    public ClienteFileDto findByIdentificacionFile(Integer identificacion) throws Exception {
            if(existsByIdentificacion(identificacion)) {
                ClienteDto clienteDto = findByIdentificacion(identificacion);
                FileImagenDto fileImagenDto = fileImagenServiceClient.findByNumeroIdentificacion(identificacion);
                if(fileImagenDto == null) {
                    fileImagenDto = FileImagenDto.builder()
                            .fileName("")
                            .fileType("")
                            .base64("")
                            .identificacion(clienteDto.getIdentificacion()).build();
                    return maptoFotocliente(clienteDto, fileImagenDto);
                }
                return maptoFotocliente(clienteDto, fileImagenDto);
            }
        return null;
    }

    private ClienteFileDto maptoFotocliente(ClienteDto cliente, FileImagenDto fileImagenDto)
    {
        ClienteFileDto clienteFileDto = ClienteFileDto.builder()
                .id(cliente.getId())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .identificacion(cliente.getIdentificacion())
                .tipoDocumento(cliente.getTipoDocumento())
                .edad(cliente.getEdad())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .ciudadNacimiento(cliente.getCiudadNacimiento())
                .fileImagenDto(fileImagenDto).build();
        return clienteFileDto;
    }

    @Override
    public ClienteDto findByIdentificacion(Integer identificacion) throws Exception {
        existsByIdentificacion(identificacion);
        Optional<ClienteEntidad> clienteEntidad =clienteInterfaceRepository.findByIdentificacion(identificacion);
        return clienteInterfaceMapper.toClienteDto(clienteEntidad.get());
    }

    @Override
    public boolean existsByIdentificacion(Integer identificacion) throws Exception{
        if(clienteInterfaceRepository.existsByIdentificacion(identificacion)) {
            return true;
        } else {
            throw new RequestException("code", HttpStatus.NOT_FOUND, ErrorsUtils.identificacionNoRegistrada(identificacion.toString()));
        }
    }

    @Override
    public List<ClienteDto> findByAge(Integer edad) throws Exception{
        List<ClienteDto> clienteDtoList = clienteInterfaceMapper.toClienteListDto(clienteInterfaceRepository.findByAge(edad));
        if(clienteDtoList.isEmpty())
        {
            throw new LogicException("code", HttpStatus.NO_CONTENT, ErrorsUtils.sinRegistros());
        }
        return clienteDtoList;
    }

    @Override
    public boolean existsByTipoDocumentoEntidad(String tipo) throws Exception{
        Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo);
        if(clienteInterfaceRepository.existsByTipoDocumento(tipoDocumentoEntidad.get().getId()) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<ClienteDto> findAllPag(Pageable pageable) throws Exception{
        Page<ClienteEntidad> clienteEntidadList = clienteInterfaceRepository.findAll(pageable);
        List<ClienteDto> clienteDtoList = clienteInterfaceMapper.toClienteListDto(clienteEntidadList.toList());
        if(clienteDtoList.isEmpty())
        {
            throw new LogicException("code", HttpStatus.CONFLICT, ErrorsUtils.sinRegistros());
        }
        Page<ClienteDto> clienteDtoPageList = new PageImpl<>(clienteDtoList);
        return clienteDtoPageList;
    }

    @Override
    public boolean validateClient(ClienteDto cliente) throws Exception {
        if (cliente.getNombres().equals("") || cliente.getNombres().equals(null))
        {
            throw new LogicException("code", HttpStatus.BAD_REQUEST, "El nombre no puede ser vacio");
        }
        if (cliente.getApellidos().equals("") || cliente.getApellidos().equals(null))
        {
            throw new LogicException("code", HttpStatus.BAD_REQUEST, "El apellido no puede ser vacio");
        }
        if (cliente.getEdad().equals(null))
        {
            throw new LogicException("code", HttpStatus.BAD_REQUEST, "La edad no puede ser vacio o tiene letras");
        }
        if (cliente.getIdentificacion().equals(null))
        {
            throw new LogicException("code", HttpStatus.BAD_REQUEST, "La identificacion no puede ser vacio o tiene letras");
        }
        if (cliente.getCiudadNacimiento().equals("") || cliente.getCiudadNacimiento().equals(null))
        {
            throw new LogicException("code", HttpStatus.BAD_REQUEST, "La ciudad no puede ser vacio");
        }
        if (cliente.getFechaNacimiento().equals("") || cliente.getFechaNacimiento().equals(null))
        {
            throw new LogicException("code", HttpStatus.BAD_REQUEST, "La fecha no puede ser vacio, formato:");
        }
        if (cliente.getTipoDocumento().equals("") || cliente.getTipoDocumento().equals(null))
        {
            throw new LogicException("code", HttpStatus.BAD_REQUEST, "El tipo de documento no puede ser vacio:");
        }
        return true;
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
