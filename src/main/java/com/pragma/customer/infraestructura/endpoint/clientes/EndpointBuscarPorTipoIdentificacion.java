package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.dominio.modelo.ClienteDto;
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
public class EndpointBuscarPorTipoIdentificacion {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @GetMapping("/identificacion/{tipo}/{numero}")
    @ApiOperation("obtiene un cliente dado su tipo y numero de identificacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = ClienteDto.class),
            @ApiResponse(code = 404, message = "no se encontro al cliente con el tipo de identificacion")
    })
    public ResponseEntity<?> obtenerPorIdentificacion(
            @PathVariable
            @ApiParam(value = "tipo de identificacion", required = true, example = "CEDULA")
                    String tipo,
            @PathVariable
            @ApiParam(value = "numero de identificacion", required = true, example = "1")
                    Integer numero
    ) throws Exception {
        ClienteDto cliente = manejadorCliente.buscarPorIdentificacion(numero);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
