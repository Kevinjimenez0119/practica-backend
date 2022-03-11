package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.infraestructura.mappers.TipoDocumentoInterfaceMapper;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.TipoDocumentoInterfaceReporsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public void save(TipoDocumentoDto tipoDocumentoDto) {
        try {
            TipoDocumentoEntidad tipoDocumentoEntidad = TipoDocumentoEntidad.builder()
                            .tipoDocumento(tipoDocumentoDto.getTipoDocumento())
                                    .build();
            tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad);
        } catch (Exception e) {
            logger.error("Error al registrar el tipo", e);
        }
    }

    @Override
    public void delete(String tipo) {
        try {
            Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo);
            tipoDocumentoInterfaceReporsitory.delete(tipoDocumentoEntidad.get());
        } catch (Exception e) {
            logger.error("Error al eliminar el cliente", e);
        }
    }

    @Override
    public void update(TipoDocumentoDto tipoDocumentoDto) {
        try {
            Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento());
            tipoDocumentoEntidad.get().setTipoDocumento(tipoDocumentoDto.getTipoDocumento());
            tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad.get());
        } catch (Exception e) {
            logger.error("Error al registrar el cliente", e);
        }
    }

    @Override
    public List<TipoDocumentoDto> findAll() {
        try {
            return tipoDocumentoInterfaceMapper.toTipoDocumentoListDto(tipoDocumentoInterfaceReporsitory.findAll());
        } catch (Exception e) {
            logger.error("Error al listar tipos de documento", e);
        }
        return new ArrayList<>();
    }

    @Override
    public TipoDocumentoDto findById(Integer id) {
        try {
            return tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoInterfaceReporsitory.findById(id).get());
        } catch (Exception e) {
            logger.error("Error busqueda de tipo por id", e);
        }
        return null;
    }

    @Override
    public TipoDocumentoDto findByTipoDocumento(String tipo) {
        try {
            return tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo).get());
        } catch (Exception e) {
            logger.error("Error busqueda de tipo por tipo", e);
        }
        return null;
    }

    @Override
    public boolean existsByTipoDocumento(String tipo) {
        try {
            return tipoDocumentoInterfaceReporsitory.existsByTipoDocumento(tipo);
        } catch (Exception e) {
            logger.error("Error al buscar cliente exist", e);
        }
        return false;
    }
}
