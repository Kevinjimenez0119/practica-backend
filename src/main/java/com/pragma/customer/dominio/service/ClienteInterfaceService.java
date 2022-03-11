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
    void save(ClienteDto cliente);

    void update(ClienteDto cliente);

    void delete(Integer id);

    List<ClienteDto> findAll();

    //FIND
    ClienteDto findById(Integer id);

    ClienteDto findByIdentificacion(Integer identificacion);

    ClienteFileDto findByIdentificacionFile(Integer identificacion);

    boolean existsByIdentificacion(Integer id);

    List<ClienteDto> findByAge(Integer edad);

    //SORTING
    Page<ClienteDto> findAllPag(Pageable pageable);

    //LOGIC
    boolean validateClient(ClienteDto cliente);

    Integer getAgeByDate(Date fechaNacimiento);

    LocalDate getBirthdayDate(Date fechaNacimiento);

}
