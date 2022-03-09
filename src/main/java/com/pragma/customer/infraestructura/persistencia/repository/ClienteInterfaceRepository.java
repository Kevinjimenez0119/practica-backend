package com.pragma.customer.infraestructura.persistencia.repository;

import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteInterfaceRepository extends JpaRepository<ClienteEntidad, Integer> {

    boolean existsByIdentificacion(Integer id);

    Optional<ClienteEntidad> findByIdentificacion(Integer identificacion);

    @Query(value = "Select c.* from cliente c where c.edad >?1", nativeQuery = true )
    List<ClienteEntidad> findByAge(Integer edad);

}
