package com.pragma.customer.service.tipodocumento;

import com.pragma.customer.data.DataTest;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import com.pragma.customer.infraestructura.exceptions.RequestException;
import com.pragma.customer.infraestructura.mappers.TipoDocumentoInterfaceMapper;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.ClienteInterfaceRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceTest {

    @InjectMocks
    TipoDocumentoServiceImpl tipoDocumentoService;

    @Mock
    TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Mock
    TipoDocumentoInterfaceMapper tipoDocumentoInterfaceMapper;

    @Mock
    ClienteInterfaceService clienteInterfaceService;

    TipoDocumentoDto tipoDocumentoDto;
    TipoDocumentoEntidad tipoDocumentoEntidad;

    @BeforeEach
    void setUp() throws ParseException {
        tipoDocumentoEntidad = DataTest.tipo1();
        tipoDocumentoDto = DataTest.tipoDto();
    }

    @Test
    void findByTipoDocumento() throws Exception {

        when(tipoDocumentoInterfaceReporsitory.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(true);

        when(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenReturn(Optional.of(tipoDocumentoEntidad));

        when(tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoEntidad)).thenReturn(tipoDocumentoDto);

        TipoDocumentoDto tipoDocumentoDtoActual = tipoDocumentoService.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento());

        assertEquals(tipoDocumentoDtoActual, tipoDocumentoDto);
    }

    @Test
    void findByTipoDocumentoException() throws Exception {

        when(tipoDocumentoInterfaceReporsitory.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())).thenThrow(RequestException.class);

        assertThrows(RequestException.class, () -> tipoDocumentoService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento()));
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
    }

    @Test
    void findAllException() throws Exception {

        when(tipoDocumentoInterfaceReporsitory.findAll()).thenThrow(LogicException.class);

        assertThrows(LogicException.class, () -> tipoDocumentoService.findAll());
    }
}
