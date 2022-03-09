package com.pragma.customer.infraestructura.endpoint.tipodocumento;

import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.Mensaje;
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
public class EndpointEliminarTipoDocumento {

    @Autowired
    private ManejadorTipoDocumento manejadorTipoDocumento;

    @DeleteMapping("/tipo/{tipo}")
    @ApiOperation("elimina un tipo de documento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "no se encontro tipo de documento")
    })
    public ResponseEntity<?> eliminar(
            @PathVariable
            @ApiParam(value = "tipo de documento", required = true, example = "CC")
                    String tipo
    ) {
        if(manejadorTipoDocumento.existeTipo(tipo) == true) {
            manejadorTipoDocumento.eliminar(tipo);
            return new ResponseEntity<>(new Mensaje("usuario con identificacion " + tipo + " eliminado"), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(ErrorsUtils.tipoIdentificacionNoRegistrada(tipo), HttpStatus.NOT_FOUND);
        }
    }
}
