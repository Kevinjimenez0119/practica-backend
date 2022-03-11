package com.pragma.customer.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileImagenDto {

    private String base64;

    private Integer identificacion;

    private String fileName;

    private String fileType;

}
