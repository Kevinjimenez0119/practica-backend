package com.pragma.customer.infraestructura.endpoint.tipodocumento;

import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.Mensaje;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.infraestructura.exceptions.RequestException;
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
public class EndpointGuardarTipoDocumento {

    @Autowired
    private ManejadorTipoDocumento manejadorTipoDocumento;

    @PostMapping("")
    @ApiOperation("guarda un tipo de identificacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 409, message = "usuario ya registrado"),
            @ApiResponse(code = 400, message = "tipo de documento ya registrada")
    })
    public ResponseEntity<?> guardarTipoDocumento(
            @RequestBody
            @ApiParam(value = "cliente", required = true) TipoDocumentoDto tipoDocumentoDto
            ) throws Exception {
                manejadorTipoDocumento.guardar(tipoDocumentoDto);
                return new ResponseEntity<>(new Mensaje("tipo de documento " + tipoDocumentoDto.getTipoDocumento() + " guardado"), HttpStatus.CREATED);
    }
}
