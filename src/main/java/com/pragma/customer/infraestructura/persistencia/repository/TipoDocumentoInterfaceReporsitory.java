package com.pragma.customer.infraestructura.persistencia.repository;

import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDocumentoInterfaceReporsitory extends JpaRepository<TipoDocumentoEntidad, Integer> {

    Optional<TipoDocumentoEntidad> findByTipoDocumento(String tipo);

    boolean existsById(Integer id);

    boolean existsByTipoDocumento(String tipo);
}
