package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ClienteInterfaceService {

    //CRUD
    boolean save(ClienteDto cliente) throws Exception;

    boolean update(ClienteDto cliente) throws Exception;

    boolean delete(Integer id) throws Exception;

    List<ClienteDto> findAll() throws Exception;

    //FIND
    ClienteDto findById(Integer id);

    ClienteDto findByIdentificacion(Integer identificacion) throws Exception;

    ClienteFileDto findByIdentificacionFile(Integer identificacion) throws Exception;

    boolean existsByIdentificacion(Integer id) throws Exception;

    List<ClienteDto> findByAge(Integer edad) throws Exception;

    boolean existsByTipoDocumentoEntidad(String tipo) throws Exception;

    //SORTING
    Page<ClienteDto> findAllPag(Pageable pageable) throws Exception;


}
