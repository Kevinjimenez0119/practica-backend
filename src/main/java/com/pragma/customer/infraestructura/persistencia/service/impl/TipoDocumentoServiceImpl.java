package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
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
    @Override
    public void save(TipoDocumentoEntidad tipoDocumentoEntidad) {
        try {
            tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad);
        } catch (Exception e) {
            logger.error("Error al registrar el tipo", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = findById(id);
            tipoDocumentoInterfaceReporsitory.delete(tipoDocumentoEntidad.get());
        } catch (Exception e) {
            logger.error("Error al eliminar el cliente", e);
        }
    }

    @Override
    public List<TipoDocumentoEntidad> findAll() {
        try {
            return (List<TipoDocumentoEntidad>) tipoDocumentoInterfaceReporsitory.findAll();
        } catch (Exception e) {
            logger.error("Error al listar tipos de documento", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<TipoDocumentoEntidad> findById(Integer id) {
        try {
            return tipoDocumentoInterfaceReporsitory.findById(id);
        } catch (Exception e) {
            logger.error("Error busqueda de tipo por id", e);
        }
        return null;
    }

    @Override
    public Optional<TipoDocumentoEntidad> findByTipoDocumento(String tipo) {
        try {
            return tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo);
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
