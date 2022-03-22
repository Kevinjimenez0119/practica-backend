package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteInterfaceService {

    //CRUD
    boolean save(ClienteDto cliente);

    boolean update(ClienteDto cliente);

    boolean delete(Integer id);

    List<ClienteDto> findAll();

    //FIND
    ClienteDto findById(Integer id);

    ClienteDto findByIdentificacion(Integer identificacion);

    ClienteFileDto findByIdentificacionFile(Integer identificacion);

    boolean existsByIdentificacion(Integer id);

    List<ClienteDto> findByAge(Integer edad);

    boolean existsByTipoDocumentoEntidad(String tipo);

    //SORTING
    Page<ClienteDto> findAllPag(Pageable pageable);


}
