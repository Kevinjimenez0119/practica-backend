package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.dominio.modelo.Cliente;
import com.pragma.customer.dominio.modelo.TipoDocumento;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.infraestructura.mappers.TipoDocumentoMapper;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.TipoDocumentoInterfaceReporsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoInterfaceService {

    Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Autowired
    private TipoDocumentoMapper tipoDocumentoMapper;

    @Override
    public void save(TipoDocumento tipoDocumento) {
        try {
            TipoDocumentoEntidad tipoDocumentoEntidad = TipoDocumentoEntidad.builder()
                            .tipoDocumento(tipoDocumento.getTipoDocumento())
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
    public void update(TipoDocumento tipoDocumento) {
        try {
            Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipoDocumento.getTipoDocumento());
            tipoDocumentoEntidad.get().setTipoDocumento(tipoDocumento.getTipoDocumento());
            tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad.get());
        } catch (Exception e) {
            logger.error("Error al registrar el cliente", e);
        }
    }

    @Override
    public List<TipoDocumento> findAll() {
        try {
            return tipoDocumentoMapper.toTipoDocumentoListDto(tipoDocumentoInterfaceReporsitory.findAll());
        } catch (Exception e) {
            logger.error("Error al listar tipos de documento", e);
        }
        return new ArrayList<>();
    }

    @Override
    public TipoDocumento findById(Integer id) {
        try {
            return tipoDocumentoMapper.toTipoDocumentoDto(tipoDocumentoInterfaceReporsitory.findById(id).get());
        } catch (Exception e) {
            logger.error("Error busqueda de tipo por id", e);
        }
        return null;
    }

    @Override
    public TipoDocumento findByTipoDocumento(String tipo) {
        try {
            return tipoDocumentoMapper.toTipoDocumentoDto(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo).get());
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
