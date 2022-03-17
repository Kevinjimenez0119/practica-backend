package com.pragma.customer.endpoints.tipodocumento;

import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.data.DataTest;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import com.pragma.customer.infraestructura.endpoint.tipodocumento.*;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EndpointsTest {

    @InjectMocks
    EndpointActualizarTipoDocumento endpointActualizarTipoDocumento;

    @InjectMocks
    EndpointEliminarTipoDocumento endpointEliminarTipoDocumento;

    @InjectMocks
    EndpointGuardarTipoDocumento endpointGuardarTipoDocumento;

    @InjectMocks
    EndpointBuscarPorTipo endpointBuscarPorTipo;

    @InjectMocks
    EndpointListarTiposDeDocumento endpointListarTiposDeDocumento;

    @Mock
    ManejadorTipoDocumento manejadorTipoDocumento;

    @Mock
    TipoDocumentoUseCase tipoDocumentoUseCase;

    TipoDocumentoDto tipoDocumentoDto;
    TipoDocumentoEntidad tipoDocumentoEntidad;

    @BeforeEach
    void setUp() throws ParseException {
        tipoDocumentoEntidad = DataTest.tipo1();
        tipoDocumentoDto = DataTest.tipoDto();
    }

    @Test
    void findByIdentificacion() throws Exception {
        ResponseEntity<?> fileResponseEntity = new ResponseEntity(tipoDocumentoDto, HttpStatus.OK);

        when(tipoDocumentoUseCase.buscarPorTipo(tipoDocumentoDto.getTipoDocumento())).thenReturn(tipoDocumentoDto);

        when(manejadorTipoDocumento.buscarPorTipo(tipoDocumentoDto.getTipoDocumento())).thenReturn(tipoDocumentoDto);

        ResponseEntity<?> response = endpointBuscarPorTipo.obtenerPorIdentificacion(tipoDocumentoDto.getTipoDocumento());

        assertEquals(fileResponseEntity, response);
    }

    @Test
    void findAll() throws Exception {
        List<TipoDocumentoDto> tipoDocumentoDtoList = new ArrayList<>();
        tipoDocumentoDtoList.add(tipoDocumentoDto);

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(tipoDocumentoDtoList, HttpStatus.OK);

        when(tipoDocumentoUseCase.listar()).thenReturn(tipoDocumentoDtoList);

        when(manejadorTipoDocumento.listar()).thenReturn(tipoDocumentoDtoList);

        ResponseEntity<?> response = endpointListarTiposDeDocumento.listarTiposDeDocumentos();

        assertEquals(fileResponseEntity, response);
    }

    @Test
    void save() throws Exception {
        ResponseEntity<?> fileResponseEntity = new ResponseEntity(HttpStatus.CREATED);

        ResponseEntity<?> response = endpointGuardarTipoDocumento.guardarTipoDocumento(tipoDocumentoDto);

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    void delete() throws Exception {
        ResponseEntity<?> fileResponseEntity = new ResponseEntity(HttpStatus.OK);

        ResponseEntity<?> response = endpointEliminarTipoDocumento.eliminar(tipoDocumentoDto.getTipoDocumento());

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    void update() throws Exception {
        ResponseEntity<?> fileResponseEntity = new ResponseEntity(HttpStatus.OK);

        ResponseEntity<?> response = endpointActualizarTipoDocumento.actualizar(tipoDocumentoDto);

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }
}
