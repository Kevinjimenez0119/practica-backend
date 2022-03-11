package com.pragma.customer.infraestructura.endpoint.clientes;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.Mensaje;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointListarClientes {

    @Autowired
    private ManejadorCliente manejadorCliente;

    @GetMapping("/listAll")
    @ApiOperation("obtiene una lista de todos los clientes ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ClienteDto.class),
            @ApiResponse(code = 204, message = "no hay ningun cliente registrado")
    })
    public ResponseEntity<?> listarClientes() {
        List<ClienteDto> clienteList = manejadorCliente.listar();
        if(clienteList.isEmpty())
        {
            throw new LogicException("code", HttpStatus.CONFLICT, ErrorsUtils.sinRegistros());
        }
        return new ResponseEntity<>(clienteList, HttpStatus.OK);
    }
}
