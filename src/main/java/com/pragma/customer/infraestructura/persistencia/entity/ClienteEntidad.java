package com.pragma.customer.infraestructura.persistencia.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEntidad implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipoDocumento", referencedColumnName = "id")
    private TipoDocumentoEntidad tipoDocumentoEntidad;

    @Column(nullable = false)
    private Integer identificacion;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private String ciudadNacimiento;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
}
