package com.pragma.customer.infraestructura.endpoint.tipodocumento;

import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.dominio.modelo.Mensaje;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipodocumento")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointActualizarTipoDocumento {

    @Autowired
    private ManejadorTipoDocumento manejadorTipoDocumento;

    @PutMapping()
    @ApiOperation("actualiza un tipo de documento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "no se encontro tipo de documento")
    })
    public ResponseEntity<?> actualizar(
            @RequestBody
            @ApiParam(value = "cliente", required = true)
                    TipoDocumentoDto tipoDocumentoDto
    ) throws Exception {
        manejadorTipoDocumento.actualizar(tipoDocumentoDto);
        return new ResponseEntity<>(new Mensaje("tipo de documento " + tipoDocumentoDto.getTipoDocumento() + " actualizado"), HttpStatus.OK);
    }
}
