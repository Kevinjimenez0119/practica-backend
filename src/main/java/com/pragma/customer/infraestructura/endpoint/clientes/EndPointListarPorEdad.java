package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.aplicacion.utils.ErrorsUtils;
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

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndPointListarPorEdad {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @GetMapping("/edad/{edad}")
    @ApiOperation("obtiene una lista de los clientes cuya edad sea mayor o igual a el parametro")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ClienteDto.class),
            @ApiResponse(code = 204, message = "no hay ningun cliente que cumpla la condicion")
    })
    public ResponseEntity<?> obtenerPorEdadMayorIgual(
            @PathVariable
            @ApiParam(value = "edad", required = true, example = "20")
                    Integer edad
    ) {
        List<ClienteDto> clienteList = manejadorCliente.listarPorEdadMayor(edad);
        if(clienteList.isEmpty())
        {
            return new ResponseEntity<>(ErrorsUtils.sinClientesPorEdad(edad), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(clienteList, HttpStatus.OK);
    }
}
