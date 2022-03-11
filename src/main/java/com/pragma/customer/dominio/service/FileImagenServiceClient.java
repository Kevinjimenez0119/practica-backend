package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.FileImagenDto;

public interface FileImagenServiceClient {

    boolean delete(Integer identificacion);

    FileImagenDto findByNumeroIdentificacion(Integer identificacion);

}
