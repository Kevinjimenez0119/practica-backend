package com.pragma.customer.infraestructura.persistencia.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tipo_documento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoDocumentoEntidad implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(nullable = false)
    private String tipoDocumento;
}
