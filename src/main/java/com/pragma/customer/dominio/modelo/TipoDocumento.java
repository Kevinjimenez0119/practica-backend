package com.pragma.customer.dominio.modelo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoDocumento {

    @ApiModelProperty(notes = "id autoincremental")
    private Integer id;

    @ApiModelProperty(notes = "Tipo de documento", required = true)
    private String tipoDocumento;

}
