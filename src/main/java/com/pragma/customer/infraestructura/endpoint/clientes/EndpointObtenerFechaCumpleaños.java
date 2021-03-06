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

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointObtenerFechaCumpleaños {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @GetMapping("/Birthday/identificacion/{numero}")
    @ApiOperation("obtiene la sig fecha de cumpleaños del cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Date.class),
            @ApiResponse(code = 404, message = "no se encontro al cliente")
    })
    public ResponseEntity<?> obtenerFechaPorIdentificacion(
            @PathVariable
            @ApiParam(value = "numero de identificacion", required = true, example = "1")
                    Integer numero
    ) throws Exception {
        ClienteDto cliente = manejadorCliente.buscarPorIdentificacion(numero);
        LocalDate birthDay = manejadorCliente.birthDay(cliente.getFechaNacimiento());
        return new ResponseEntity<>(birthDay, HttpStatus.OK);
    }
}
