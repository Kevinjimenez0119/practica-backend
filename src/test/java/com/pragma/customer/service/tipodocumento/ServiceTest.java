package com.pragma.customer.service.tipodocumento;

import com.pragma.customer.data.DataTest;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import com.pragma.customer.infraestructura.exceptions.RequestException;
import com.pragma.customer.infraestructura.mappers.TipoDocumentoInterfaceMapper;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.TipoDocumentoInterfaceReporsitory;
import com.pragma.customer.infraestructura.persistencia.service.impl.TipoDocumentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceTest {

    @InjectMocks
    TipoDocumentoUseCase tipoDocumentoUseCase;

    @Mock
    TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    @Mock
    ClienteUseCase clienteUseCase;

    @InjectMocks
    TipoDocumentoServiceImpl tipoDocumentoService;

    @Mock
    TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Mock
    TipoDocumentoInterfaceMapper tipoDocumentoInterfaceMapper;

    TipoDocumentoDto tipoDocumentoDto;
    TipoDocumentoEntidad tipoDocumentoEntidad;

    @BeforeEach
    void setUp() throws ParseException {
        tipoDocumentoEntidad = DataTest.tipo1();
        tipoDocumentoDto = DataTest.tipoDto();
    }

    @Test
    void findById() throws Exception {
        when(tipoDocumentoInterfaceReporsitory.findById(tipoDocumentoDto.getId())).thenReturn(Optional.ofNullable(tipoDocumentoEntidad));

        when(tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoEntidad)).thenReturn(tipoDocumentoDto);

        TipoDocumentoDto tipoDocumentoDtoActual = tipoDocumentoService.findById(tipoDocumentoDto.getId());

        assertEquals(tipoDocumentoDto, tipoDocumentoDtoActual);
    }

    @Test
    void findByTipoDocumento() throws Exception {

        when(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(Optional.of(tipoDocumentoEntidad));

        when(tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoEntidad)).thenReturn(tipoDocumentoDto);

        TipoDocumentoDto tipoDocumentoDtoActual = tipoDocumentoService.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento());

        assertEquals(tipoDocumentoDto, tipoDocumentoDtoActual);

        when(tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(tipoDocumentoUseCase.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(tipoDocumentoService.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(tipoDocumentoDto);

        when(tipoDocumentoInterfaceService.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(tipoDocumentoDto);

        TipoDocumentoDto tipoDocumentoDto1 = tipoDocumentoUseCase.buscarPorTipo(tipoDocumentoDto.getTipoDocumento());

        assertEquals(tipoDocumentoDto, tipoDocumentoDto1);
    }

    @Test
    void findByTipoDocumentoException() throws Exception {
        when(tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenThrow(RequestException.class);

        assertThrows(RequestException.class, () -> tipoDocumentoUseCase.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento()));
    }

    @Test
    void findAll() throws Exception {
        List<TipoDocumentoDto> tipoDocumentoDtoList = new ArrayList<>();
        tipoDocumentoDtoList.add(tipoDocumentoDto);

        List<TipoDocumentoEntidad> tipoDocumentoEntidadList = new ArrayList<>();
        tipoDocumentoEntidadList.add(tipoDocumentoEntidad);

        when(tipoDocumentoInterfaceReporsitory.findAll()).thenReturn(tipoDocumentoEntidadList);

        when(tipoDocumentoInterfaceMapper.toTipoDocumentoListDto(tipoDocumentoEntidadList)).thenReturn(tipoDocumentoDtoList);

        List<TipoDocumentoDto> tipoDocumentoDtoListActual = tipoDocumentoService.findAll();

        assertEquals(tipoDocumentoDtoListActual, tipoDocumentoDtoList);

        assertEquals(1, tipoDocumentoDtoListActual.size());

        when(tipoDocumentoService.findAll()).thenReturn(tipoDocumentoDtoList);

        when(tipoDocumentoInterfaceService.findAll()).thenReturn(tipoDocumentoDtoList);

        List<TipoDocumentoDto> tipoDocumentoDtoListActual2 = tipoDocumentoUseCase.listar();

        assertEquals(tipoDocumentoDtoList, tipoDocumentoDtoListActual2);
    }

    @Test
    void findAllException() throws Exception {
        List<TipoDocumentoEntidad> tipoDocumentoEntidadList = new ArrayList<>();

        List<TipoDocumentoDto> tipoDocumentoDtoList = new ArrayList<>();

        when(tipoDocumentoService.findAll()).thenReturn(tipoDocumentoDtoList);

        when(tipoDocumentoInterfaceService.findAll()).thenReturn(tipoDocumentoDtoList);

        assertThrows(LogicException.class, () -> tipoDocumentoUseCase.listar());
    }

    @Test
    void ExceptionExist() throws Exception {
        when(tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(false);

        assertThrows(RequestException.class, () -> tipoDocumentoUseCase.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento()));

    }

    @Test
    void save() throws Exception {
        when(tipoDocumentoInterfaceMapper.toTipoDocumentoEntidad(tipoDocumentoDto)).thenReturn(tipoDocumentoEntidad);

        tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad);

        boolean response = tipoDocumentoService.save(tipoDocumentoDto);

        assertEquals(true, response);

        when(tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(false);

        when(tipoDocumentoService.save(tipoDocumentoDto)).thenReturn(true);

        when(tipoDocumentoInterfaceService.save(tipoDocumentoDto)).thenReturn(true);

        boolean responseActual = tipoDocumentoUseCase.guardar(tipoDocumentoDto);

        assertTrue(responseActual);
    }

    @Test
    void saveExceptionExist() throws Exception {
        when(tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        assertThrows(RequestException.class, () -> tipoDocumentoUseCase.guardar(tipoDocumentoDto));
    }

    @Test
    void delete() throws Exception {
        when(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(Optional.ofNullable(tipoDocumentoEntidad));

        tipoDocumentoInterfaceReporsitory.delete(tipoDocumentoEntidad);

        boolean response = tipoDocumentoService.delete(tipoDocumentoDto.getTipoDocumento());

        assertTrue(response);

        when(tipoDocumentoService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(clienteUseCase.existsByTipoDocumentoEntidad(tipoDocumentoDto.getTipoDocumento())).thenReturn(false);

        when(tipoDocumentoInterfaceService.delete(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        boolean responseActual = tipoDocumentoUseCase.eliminar(tipoDocumentoDto.getTipoDocumento());

        assertTrue(responseActual);
    }

    @Test
    void deleteException() throws Exception {
        when(tipoDocumentoService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(clienteUseCase.existsByTipoDocumentoEntidad(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        assertThrows(RequestException.class, () -> tipoDocumentoUseCase.eliminar(tipoDocumentoDto.getTipoDocumento()));
    }

    @Test
    void update() throws Exception {
        when(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(Optional.ofNullable(tipoDocumentoEntidad));

        tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad);

        boolean response = tipoDocumentoService.update(tipoDocumentoDto);

        assertTrue(response);

        when(tipoDocumentoService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(tipoDocumentoService.update(tipoDocumentoDto)).thenReturn(true);

        when(tipoDocumentoInterfaceService.update(tipoDocumentoDto)).thenReturn(true);

        boolean responseActual = tipoDocumentoUseCase.actualizar(tipoDocumentoDto);

        assertTrue(responseActual);
    }
}
