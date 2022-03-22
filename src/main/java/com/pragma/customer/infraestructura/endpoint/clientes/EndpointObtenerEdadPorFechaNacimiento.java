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
public class EndpointObtenerEdadPorFechaNacimiento {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @GetMapping("/edad/identificacion/{numero}")
    @ApiOperation("obtiene la edad por la fecha de nacimiento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
            @ApiResponse(code = 404, message = "no se encontro al cliente")
    })
    public ResponseEntity<?> obtenerEdadPorIdentificacion(
            @PathVariable
            @ApiParam(value = "numero de identificacion", required = true, example = "1")
                    Integer numero
    ) throws Exception {
        ClienteDto cliente = manejadorCliente.buscarPorIdentificacion(numero);
        Integer edad = manejadorCliente.edadPorFecha(cliente.getFechaNacimiento());
        return new ResponseEntity<>(edad, HttpStatus.OK);

    }
}
