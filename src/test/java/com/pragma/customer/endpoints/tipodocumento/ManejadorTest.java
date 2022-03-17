package com.pragma.customer.endpoints.tipodocumento;

import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.data.DataTest;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
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
public class ManejadorTest {

    @InjectMocks
    ManejadorTipoDocumento manejadorTipoDocumento;

    @Mock
    TipoDocumentoUseCase tipoDocumentoUseCase;

    TipoDocumentoDto tipoDocumentoDto;
    TipoDocumentoEntidad tipoDocumentoEntidad;

    @BeforeEach
    void setUp() {
        tipoDocumentoEntidad = DataTest.tipo1();
        tipoDocumentoDto = DataTest.tipoDto();
    }

    @Test
    void findByIdentificacion() throws Exception {
        when(tipoDocumentoUseCase.buscarPorTipo(tipoDocumentoDto.getTipoDocumento())).thenReturn(tipoDocumentoDto);

        TipoDocumentoDto tipoDocumentoDtoActual = manejadorTipoDocumento.buscarPorTipo(tipoDocumentoDto.getTipoDocumento());

        assertEquals(tipoDocumentoDto, tipoDocumentoDtoActual);
    }

    @Test
    void findAll() throws Exception {
        List<TipoDocumentoDto> tipoDocumentoDtoList = new ArrayList<>();
        tipoDocumentoDtoList.add(tipoDocumentoDto);

        when(tipoDocumentoUseCase.listar()).thenReturn(tipoDocumentoDtoList);

        List<TipoDocumentoDto> tipoDocumentoDtoListActual = manejadorTipoDocumento.listar();

        assertEquals(tipoDocumentoDtoList, tipoDocumentoDtoListActual);
    }

    @Test
    void save() throws Exception {
        when(tipoDocumentoUseCase.guardar(tipoDocumentoDto)).thenReturn(true);

        boolean response = manejadorTipoDocumento.guardar(tipoDocumentoDto);

        assertEquals(true, response);
    }

    @Test
    void delete() throws Exception {
        when(tipoDocumentoUseCase.eliminar(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        boolean response = manejadorTipoDocumento.eliminar(tipoDocumentoDto.getTipoDocumento());

        assertEquals(true, response);
    }

    @Test
    void update() throws Exception {
        when(tipoDocumentoUseCase.actualizar(tipoDocumentoDto)).thenReturn(true);

        boolean response = manejadorTipoDocumento.actualizar(tipoDocumentoDto);

        assertEquals(true, response);
    }
}
