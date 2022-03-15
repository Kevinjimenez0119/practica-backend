package com.pragma.customer.service;

import com.pragma.customer.data.DataTest;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.modelo.FileImagenDto;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.FileImagenServiceClient;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import com.pragma.customer.infraestructura.exceptions.RequestException;
import com.pragma.customer.infraestructura.mappers.ClienteInterfaceMapper;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.ClienteInterfaceRepository;
import com.pragma.customer.infraestructura.persistencia.repository.TipoDocumentoInterfaceReporsitory;
import com.pragma.customer.infraestructura.persistencia.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private ClienteServiceImpl clienteService;

    @MockBean
    private ClienteInterfaceRepository clienteInterfaceRepository;

    @MockBean
    private TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @MockBean
    private ClienteInterfaceMapper clienteInterfaceMapper;

    @MockBean
    private TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    @MockBean
    private FileImagenServiceClient fileImagenServiceClient;

    ClienteDto clienteDto;
    TipoDocumentoDto tipoDocumentoDto;
    ClienteEntidad clienteEntidad;
    TipoDocumentoEntidad tipoDocumentoEntidad;
    FileImagenDto fileImagenDto;
    ClienteFileDto clienteFileDto;

   @BeforeEach
   void setUp() throws ParseException {
       clienteDto = DataTest.cliente1();
       clienteEntidad = DataTest.clienteEntidad();
       fileImagenDto = DataTest.fileImagenDto();
       tipoDocumentoEntidad = DataTest.tipo1();
       clienteFileDto = DataTest.clienteFileDto();
   }

    @Test
    void findByIdentificacion() throws Exception {

        when(clienteInterfaceRepository.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(clienteInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(Optional.of(clienteEntidad));

        when(clienteInterfaceMapper.toClienteDto(clienteEntidad)).thenReturn(clienteDto);

        ClienteDto clienteActual = clienteService.findByIdentificacion(clienteDto.getIdentificacion());

        assertEquals(clienteDto, clienteActual);
    }

    @Test
    void findByIdentificacionException() throws Exception {

        when(clienteInterfaceRepository.existsByIdentificacion(clienteDto.getIdentificacion())).thenThrow(RequestException.class);

        assertThrows(RequestException.class, () -> clienteService.findByIdentificacion(clienteDto.getIdentificacion()));
    }

    @Test
    void findAll() throws Exception {
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        clienteDtoList.add(clienteDto);

        List<ClienteEntidad> clienteEntidadList = new ArrayList<>();
        clienteEntidadList.add(clienteEntidad);

        when(clienteInterfaceRepository.findAll()).thenReturn(clienteEntidadList);

        when(clienteInterfaceMapper.toClienteListDto(clienteEntidadList)).thenReturn(clienteDtoList);

        List<ClienteDto> listaActual = clienteService.findAll();

        assertEquals(clienteDtoList, listaActual);

        assertEquals(1, listaActual.size());
    }

    @Test
    void findAllException() throws Exception {

        when(clienteInterfaceRepository.findAll()).thenThrow(LogicException.class);

        assertThrows(LogicException.class, () -> clienteService.findAll());
    }

    @Test
    void findByIdentificacionFile() throws Exception {

        when(clienteInterfaceRepository.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(clienteInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(Optional.of(clienteEntidad));

        when(clienteInterfaceMapper.toClienteDto(clienteEntidad)).thenReturn(clienteDto);

        when(fileImagenServiceClient.findByNumeroIdentificacion(clienteDto.getIdentificacion())).thenReturn(fileImagenDto);

        ClienteFileDto clienteActual = clienteService.findByIdentificacionFile(clienteDto.getIdentificacion());

        assertEquals(clienteFileDto, clienteActual);
    }

    @Test
    void findByIdentificacionFilenull() throws Exception {
        ClienteFileDto clienteFileDto = DataTest.clienteFileDtonull();

        when(clienteInterfaceRepository.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(clienteInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(Optional.of(clienteEntidad));

        when(clienteInterfaceMapper.toClienteDto(clienteEntidad)).thenReturn(clienteDto);

        when(fileImagenServiceClient.findByNumeroIdentificacion(clienteDto.getIdentificacion())).thenReturn(null);

        ClienteFileDto clienteActual = clienteService.findByIdentificacionFile(clienteDto.getIdentificacion());

        assertEquals(clienteFileDto, clienteActual);
    }

    @Test
    void findByAge() throws Exception {
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        clienteDtoList.add(clienteDto);

        List<ClienteEntidad> clienteEntidadList = new ArrayList<>();
        clienteEntidadList.add(clienteEntidad);

        when(clienteInterfaceRepository.findByAge(18)).thenReturn(clienteEntidadList);

        when(clienteInterfaceMapper.toClienteListDto(clienteEntidadList)).thenReturn(clienteDtoList);

        List<ClienteDto> listaActual = clienteService.findByAge(18);

        assertEquals(clienteDtoList, listaActual);

        assertEquals(1, listaActual.size());
    }

    @Test
    void findByAgeException() throws Exception {

        when(clienteInterfaceRepository.findByAge(39)).thenThrow(LogicException.class);

        assertThrows(LogicException.class, () -> clienteService.findByAge(39));
    }

}
