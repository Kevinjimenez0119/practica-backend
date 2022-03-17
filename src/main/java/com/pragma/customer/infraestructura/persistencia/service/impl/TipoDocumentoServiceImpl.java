package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import com.pragma.customer.infraestructura.exceptions.RequestException;
import com.pragma.customer.infraestructura.mappers.TipoDocumentoInterfaceMapper;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.TipoDocumentoInterfaceReporsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoDocumentoServiceImpl implements TipoDocumentoInterfaceService {

    Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Autowired
    private TipoDocumentoInterfaceMapper tipoDocumentoInterfaceMapper;

    @Autowired
    private ClienteInterfaceService clienteInterfaceService;

    @Override
    public boolean save(TipoDocumentoDto tipoDocumentoDto) throws Exception{
        if (!tipoDocumentoInterfaceReporsitory.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())) {
            TipoDocumentoEntidad tipoDocumentoEntidad = TipoDocumentoEntidad.builder()
                    .tipoDocumento(tipoDocumentoDto.getTipoDocumento())
                    .build();
            tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad);
            return true;
        } else {
            throw new RequestException("code", HttpStatus.BAD_REQUEST, ErrorsUtils.tipoIdentificacionRegistrada(tipoDocumentoDto.getTipoDocumento()));
        }
    }

    @Override
    public boolean delete(String tipo) throws Exception {
        if(existsByTipoDocumento(tipo)) {
            if(!clienteInterfaceService.existsByTipoDocumentoEntidad(tipo)) {
                Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo);
                tipoDocumentoInterfaceReporsitory.delete(tipoDocumentoEntidad.get());
                return true;
            } else {
                throw new RequestException("code", HttpStatus.BAD_REQUEST, "el tipo de documento lo tienen algunos clientes");
            }
        }
        return false;
    }

    @Override
    public boolean update(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        if(existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())) {
            Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento());
            tipoDocumentoEntidad.get().setTipoDocumento(tipoDocumentoDto.getTipoDocumento());
            tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad.get());
            return true;
        }
        return false;
    }

    @Override
    public List<TipoDocumentoDto> findAll() throws Exception {
        List<TipoDocumentoDto> tipoDocumentoDtoList = tipoDocumentoInterfaceMapper.toTipoDocumentoListDto(tipoDocumentoInterfaceReporsitory.findAll());
        if(tipoDocumentoDtoList.isEmpty())
        {
            throw new LogicException("code", HttpStatus.CONFLICT, ErrorsUtils.sinRegistros());
        }
        return tipoDocumentoDtoList;
    }

    @Override
    public TipoDocumentoDto findById(Integer id) {
        return tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoInterfaceReporsitory.findById(id).get());
    }

    @Override
    public TipoDocumentoDto findByTipoDocumento(String tipo) throws Exception{
        existsByTipoDocumento(tipo);
        return tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo).get());
    }

    @Override
    public boolean existsByTipoDocumento(String tipo) throws Exception{
        if(tipoDocumentoInterfaceReporsitory.existsByTipoDocumento(tipo)) {
            return true;
        } else {
            throw new RequestException("code", HttpStatus.BAD_REQUEST, ErrorsUtils.tipoIdentificacionNoRegistrada(tipo));
        }
    }
}
