package com.pragma.customer.dominio.service;

import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;

import java.util.List;
import java.util.Optional;

public interface TipoDocumentoInterfaceService {

    //CRUD
    void save(TipoDocumentoEntidad tipoDocumentoEntidad);

    void delete(Integer id);

    List<TipoDocumentoEntidad> findAll();

    //FIND
    Optional<TipoDocumentoEntidad> findById(Integer id);

    Optional<TipoDocumentoEntidad> findByTipoDocumento(String tipo);

    boolean existsByTipoDocumento(String tipo);

}
