package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.FileImagen;

public interface FileImagenServiceClient {

    boolean delete(Integer identificacion);

    FileImagen findByNumeroIdentificacion(Integer identificacion);

}
