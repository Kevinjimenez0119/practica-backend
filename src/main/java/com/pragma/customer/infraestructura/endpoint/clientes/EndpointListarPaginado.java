package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.dominio.modelo.Mensaje;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointListarPaginado {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @GetMapping("/list")
    @ApiOperation("obtiene una lista de todos los clientes ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "no hay ningun cliente registrado")
    })
    public ResponseEntity<?> listarClientes(
            @PageableDefault(size = 10, page = 0) Pageable pageable) throws Exception {
        Page<ClienteEntidad> clienteEntidads = manejadorCliente.listarPag(pageable);
        if(clienteEntidads.isEmpty())
        {
            return new ResponseEntity<>(new Mensaje("No hay registros"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clienteEntidads, HttpStatus.OK);
    }
}
