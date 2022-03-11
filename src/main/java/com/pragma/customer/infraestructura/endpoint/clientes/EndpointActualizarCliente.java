package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.Mensaje;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointActualizarCliente {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @Autowired
    private ManejadorTipoDocumento manejadorTipoDocumento;

    @PutMapping()
    @ApiOperation("actualiza un cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "identificacion no valida"),
            @ApiResponse(code = 404, message = "no se encontro al cliente")
    })
    public ResponseEntity<?> actualizar(
            @RequestBody
            @ApiParam(value = "cliente", required = true)
                    ClienteDto cliente
    ) {
        if(manejadorCliente.existeCliente(cliente.getIdentificacion()) == true) {
            if(manejadorTipoDocumento.existeTipo(cliente.getTipoDocumento()) == true) {
                manejadorCliente.actualizar(cliente);
                return new ResponseEntity<>(new Mensaje("usuario " + cliente.getNombres() + " actualizado"), HttpStatus.OK);
            } else {
                throw new RequestException("code", HttpStatus.BAD_REQUEST, ErrorsUtils.tipoIdentificacionNoRegistrada(cliente.getIdentificacion().toString()));
            }
        }else {
            throw new RequestException("code", HttpStatus.NOT_FOUND, ErrorsUtils.identificacionNoRegistrada(cliente.getIdentificacion().toString()));
        }
    }
}
