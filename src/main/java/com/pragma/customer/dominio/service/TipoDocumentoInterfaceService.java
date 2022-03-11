package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;

import java.util.List;

public interface TipoDocumentoInterfaceService {

    //CRUD
    void save(TipoDocumentoDto tipoDocumentoDto);

    void delete(String tipo);

    void update(TipoDocumentoDto tipoDocumentoDto);

    List<TipoDocumentoDto> findAll();

    //FIND
    TipoDocumentoDto findById(Integer id);

    TipoDocumentoDto findByTipoDocumento(String tipo);

    boolean existsByTipoDocumento(String tipo);

}
