package com.pragma.customer.dominio.service;

import com.pragma.customer.dominio.modelo.Cliente;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClienteInterfaceService {

    //CRUD
    void save(Cliente cliente);

    void update(Cliente cliente);

    void delete(Integer id);

    List<Cliente> findAll();

    //FIND
    Cliente findById(Integer id);

    Cliente findByIdentificacion(Integer identificacion);

    boolean existsByIdentificacion(Integer id);

    List<Cliente> findByAge(Integer edad);

    //SORTING
    Page<Cliente> findAllPag(Pageable pageable);

    //LOGIC
    boolean validateClient(Cliente cliente);

    Integer getAgeByDate(Date fechaNacimiento);

    LocalDate getBirthdayDate(Date fechaNacimiento);

}
