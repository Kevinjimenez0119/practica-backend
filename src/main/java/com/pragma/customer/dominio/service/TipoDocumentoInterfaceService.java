package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.Cliente;
import com.pragma.customer.dominio.modelo.TipoDocumento;

import java.util.List;
import java.util.Optional;

public interface TipoDocumentoInterfaceService {

    //CRUD
    void save(TipoDocumento tipoDocumento);

    void delete(String tipo);

    void update(TipoDocumento tipoDocumento);

    List<TipoDocumento> findAll();

    //FIND
    TipoDocumento findById(Integer id);

    TipoDocumento findByTipoDocumento(String tipo);

    boolean existsByTipoDocumento(String tipo);

}
