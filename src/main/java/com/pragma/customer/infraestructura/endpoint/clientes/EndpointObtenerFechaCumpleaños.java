package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.dominio.modelo.Mensaje;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
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
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "no se encontro al cliente")
    })
    public ResponseEntity<?> obtenerFechaPorIdentificacion(
            @PathVariable
            @ApiParam(value = "numero de identificacion", required = true, example = "1")
                    Integer numero
    ) throws Exception {

        if(manejadorCliente.existeCliente(numero) == true )
        {
            ClienteEntidad clienteEntidad = manejadorCliente.buscarPorIdentificacion(numero);
            LocalDate birthDay = manejadorCliente.birthDay(clienteEntidad.getFechaNacimiento());
            return new ResponseEntity<>(birthDay, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new Mensaje("no hay ningun cliente que con identificacion " + numero ), HttpStatus.NOT_FOUND);

        }



    }
}
