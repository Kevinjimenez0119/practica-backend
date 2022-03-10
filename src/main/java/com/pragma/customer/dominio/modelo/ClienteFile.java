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
public class ClienteFile {

    @ApiModelProperty(notes = "id autoincremental", position = 1)
    private Integer id;

    @ApiModelProperty(notes = "Nombres del cliente", required = true, position = 2)
    private String nombres;

    @ApiModelProperty(notes = "Apellidos del cliente", required = true, position = 3)
    private String apellidos;

    @ApiModelProperty(notes = "Tipo de documento registrado en la base de datos", required = true, position = 4)
    private String tipoDocumento;

    @ApiModelProperty(notes = "Numero de identificacion del cliente", required = true, position = 5)
    private Integer identificacion;

    @ApiModelProperty(notes = "Edad del cliente", required = true, position = 6)
    private Integer edad;

    @ApiModelProperty(notes = "Ciudad de nacimiento del cliente", required = true, position = 7)
    private String ciudadNacimiento;

    @ApiModelProperty(notes = "Fecha de nacimiento del cliente", required = true, position = 8)
    private Date fechaNacimiento;

    @ApiModelProperty(position = 9)
    private FileImagen fileImagen;
}
