package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointEliminarCliente {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @DeleteMapping("/identificacion/{numero}")
    @ApiOperation("elimina un cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "no se encontro al cliente")
    })
    public ResponseEntity<?> eliminar(
            @PathVariable
            @ApiParam(value = "numero de identificacion", required = true, example = "1")
                    Integer numero
    ) throws Exception {
        manejadorCliente.eliminar(numero);
        return new ResponseEntity<>(new Mensaje("usuario con identificacion " + numero + " eliminado"), HttpStatus.OK);
    }
}
