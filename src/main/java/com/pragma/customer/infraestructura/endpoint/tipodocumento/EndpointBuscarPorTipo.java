package com.pragma.customer.infraestructura.endpoint.tipodocumento;

import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.aplicacion.utils.ErrorsUtils;
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
public class EndpointBuscarPorTipo {

    @Autowired
    private ManejadorTipoDocumento manejadorTipoDocumento;

    @GetMapping("/tipodocumento/{tipo}")
    @ApiOperation("obtiene el tipo de documento por la palabra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = TipoDocumentoDto.class),
            @ApiResponse(code = 404, message = "no se encontro el tipo de documento")
    })
    public ResponseEntity<?> obtenerPorIdentificacion(
            @PathVariable
            @ApiParam(value = "tipo de documento", required = true, example = "CC")
                    String tipo
    ) {

        if(manejadorTipoDocumento.existeTipo(tipo) == true)
        {
            TipoDocumentoDto tipoDocumentoDto = manejadorTipoDocumento.buscarPorTipo(tipo);
            return new ResponseEntity(tipoDocumentoDto, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(ErrorsUtils.tipoIdentificacionNoRegistrada(tipo), HttpStatus.NOT_FOUND);

        }
    }
}
