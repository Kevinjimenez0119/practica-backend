package com.pragma.customer.dominio.modelo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @ApiModelProperty(notes = "id autoincremental")
    private Integer id;

    @ApiModelProperty(notes = "Nombres del cliente", required = true)
    private String nombres;

    @ApiModelProperty(notes = "Apellidos del cliente", required = true)
    private String apellidos;

    @ApiModelProperty(notes = "Tipo de documento registrado en la base de datos", required = true)
    private String tipoDocumento;

    @ApiModelProperty(notes = "Numero de identificacion del cliente", required = true)
    private Integer identificacion;

    @ApiModelProperty(notes = "Edad del cliente", required = true)
    private Integer edad;

    @ApiModelProperty(notes = "Ciudad de nacimiento del cliente", required = true)
    private String ciudadNacimiento;

    @ApiModelProperty(notes = "Fecha de nacimiento del cliente", required = true)
    private Date fechaNacimiento;
}
