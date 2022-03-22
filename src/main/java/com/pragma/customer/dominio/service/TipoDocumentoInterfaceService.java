package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;

import java.util.List;

public interface TipoDocumentoInterfaceService {

    //CRUD
    boolean save(TipoDocumentoDto tipoDocumentoDto);

    boolean delete(String tipo);

    boolean update(TipoDocumentoDto tipoDocumentoDto);

    List<TipoDocumentoDto> findAll();

    //FIND
    TipoDocumentoDto findById(Integer id);

    TipoDocumentoDto findByTipoDocumento(String tipo);

    boolean existsByTipoDocumento(String tipo);

}
