package com.pragma.customer.infraestructura.endpoint.tipodocumento;

import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.Mensaje;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
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
@RequestMapping("/api/tipodocumento")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class EndpointListarTiposDeDocumento {

    @Autowired
    private ManejadorTipoDocumento manejadorTipoDocumento;

    @GetMapping("/listAll")
    @ApiOperation("obtiene una lista de todos los tipos de documentos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = TipoDocumentoDto.class),
            @ApiResponse(code = 204, message = "no hay ningun tipo de documento registrado")
    })
    public ResponseEntity<?> listarTiposDeDocumentos() {
        List<TipoDocumentoDto> tipoDocumentoDtoList = manejadorTipoDocumento.listar();
        if(tipoDocumentoDtoList.isEmpty())
        {
            throw new LogicException("code", HttpStatus.CONFLICT, ErrorsUtils.sinRegistros());
        }
        return new ResponseEntity<>(tipoDocumentoDtoList, HttpStatus.OK);
    }
}
