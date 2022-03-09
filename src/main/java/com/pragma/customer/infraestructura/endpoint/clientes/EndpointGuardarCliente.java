package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.Cliente;
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
public class EndpointGuardarCliente {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @Autowired
    private ManejadorTipoDocumento manejadorTipoDocumento;

    @PostMapping("/save")
    @ApiOperation("guarda un cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "usuario ya registrado o identificacion no valida")
    })
    public ResponseEntity<?> guardarCliente(
            @RequestBody
            @ApiParam(value = "cliente", required = true) Cliente cliente
    ) {
        if(manejadorCliente.existeCliente(cliente.getIdentificacion()) == false) {
            if(manejadorTipoDocumento.existeTipo(cliente.getTipoDocumento())) {
                manejadorCliente.guardar(cliente);
                return new ResponseEntity<>(new Mensaje("usuario " + cliente.getNombres() + " guardado"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(ErrorsUtils.tipoIdentificacionNoRegistrada(cliente.getIdentificacion().toString()), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(ErrorsUtils.identificacionYaRegistrada(cliente.getIdentificacion().toString()), HttpStatus.BAD_REQUEST);
        }
    }
}
