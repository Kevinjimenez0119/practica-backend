package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @Autowired
    private ClienteInterfaceRepository clienteInterfaceRepository;

    @Autowired
    private TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Autowired
    private ClienteInterfaceMapper clienteInterfaceMapper;

    @Autowired
    private FileImagenServiceClient fileImagenServiceClient;

    @Override
    public boolean save(ClienteDto cliente) throws Exception {
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
        return true;
    }

    @Override
    public boolean update(ClienteDto cliente) throws Exception{
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
        return true;
    }

    @Override
    public boolean delete(Integer identificacion) throws Exception{
        FileImagenDto fileImagenDto = fileImagenServiceClient.findByNumeroIdentificacion(identificacion);
        if(fileImagenDto.getFileName().equals("none")) throw new LogicException(409, "Error al eliminar, intente mas tarde");
        if(fileImagenDto == null) {
            Optional<ClienteEntidad> clienteEntidad = clienteInterfaceRepository.findByIdentificacion(identificacion);
            clienteInterfaceRepository.delete(clienteEntidad.get());
            return true;
        } else {
            if(fileImagenServiceClient.delete(identificacion)) {
                Optional<ClienteEntidad> clienteEntidad = clienteInterfaceRepository.findByIdentificacion(identificacion);
                clienteInterfaceRepository.delete(clienteEntidad.get());
                return true;
            } else {
                throw new LogicException(409, "Error al eliminar, intente mas tarde");
            }
        }
    }

    @Override
    public List<ClienteDto> findAll() throws Exception{
        List<ClienteDto> clienteDtoList = clienteInterfaceMapper.toClienteListDto(clienteInterfaceRepository.findAll());
        return clienteDtoList;
    }

    @Override
    public ClienteDto findById(Integer id) {
        return clienteInterfaceMapper.toClienteDto(clienteInterfaceRepository.findById(id).get());
    }


    @Override
    public ClienteFileDto findByIdentificacionFile(Integer identificacion) throws Exception {
        ClienteDto clienteDto = findByIdentificacion(identificacion);
        FileImagenDto fileImagenDto = fileImagenServiceClient.findByNumeroIdentificacion(identificacion);
        if(fileImagenDto == null || fileImagenDto.getFileName().equals("none")) {
            fileImagenDto = FileImagenDto.builder()
                    .fileName("")
                    .fileType("")
                    .base64("")
                    .identificacion(clienteDto.getIdentificacion()).build();
            return maptoFotocliente(clienteDto, fileImagenDto);
        }
        return maptoFotocliente(clienteDto, fileImagenDto);
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
        Optional<ClienteEntidad> clienteEntidad =clienteInterfaceRepository.findByIdentificacion(identificacion);
        return clienteInterfaceMapper.toClienteDto(clienteEntidad.get());
    }

    @Override
    public boolean existsByIdentificacion(Integer identificacion) throws Exception{
        return clienteInterfaceRepository.existsByIdentificacion(identificacion);
    }

    @Override
    public List<ClienteDto> findByAge(Integer edad) throws Exception{
        List<ClienteDto> clienteDtoList = clienteInterfaceMapper.toClienteListDto(clienteInterfaceRepository.findByAge(edad));
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
            throw new LogicException(204, ErrorsUtils.sinRegistros());
        }
        Page<ClienteDto> clienteDtoPageList = new PageImpl<>(clienteDtoList);
        return clienteDtoPageList;
    }
}
