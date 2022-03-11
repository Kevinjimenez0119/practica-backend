package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
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
public class EndpointObtenerClienteConFile {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @Autowired
    private ManejadorTipoDocumento manejadorTipoDocumento;

    @GetMapping("/identificacionfile/{tipo}/{numero}")
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
    ) {
        if(manejadorCliente.existeCliente(numero) == true && manejadorTipoDocumento.existeTipo(tipo) == true)
        {
            ClienteFileDto clienteFile = manejadorCliente.buscarPorIdentificacionFile(numero);
            if (clienteFile.getTipoDocumento().equals(tipo)) {
                return new ResponseEntity(clienteFile, HttpStatus.OK);
            }
            return new ResponseEntity<>(new Mensaje("no hay ningun cliente que con identificacion " + numero + " con tipo de documento " + tipo), HttpStatus.NOT_FOUND);

        }else{
            return new ResponseEntity<>(new Mensaje("no hay ningun cliente que con identificacion " + numero + " con tipo de documento " + tipo), HttpStatus.NOT_FOUND);

        }
    }
}
