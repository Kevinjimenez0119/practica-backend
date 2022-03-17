package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;

import java.util.List;

public interface TipoDocumentoInterfaceService {

    //CRUD
    boolean save(TipoDocumentoDto tipoDocumentoDto) throws Exception;

    boolean delete(String tipo) throws Exception;

    boolean update(TipoDocumentoDto tipoDocumentoDto) throws Exception;

    List<TipoDocumentoDto> findAll() throws Exception;

    //FIND
    TipoDocumentoDto findById(Integer id);

    TipoDocumentoDto findByTipoDocumento(String tipo) throws Exception;

    boolean existsByTipoDocumento(String tipo) throws Exception;

}
