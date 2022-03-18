package com.pragma.customer.service.cliente;

import com.pragma.customer.data.DataTest;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.modelo.FileImagenDto;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.service.FileImagenServiceClient;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
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
    ClienteUseCase clienteUseCase;

    @Mock
    ClienteInterfaceService clienteInterfaceService;

    @Mock
    TipoDocumentoUseCase tipoDocumentoUseCase;

    @InjectMocks
    ClienteServiceImpl clienteService;

    @Mock
    ClienteInterfaceRepository clienteInterfaceRepository;

    @Mock
    TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Mock
    ClienteInterfaceMapper clienteInterfaceMapper;

    @Mock
    FileImagenServiceClient fileImagenServiceClient;

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
    void findById() throws Exception {
        when(clienteInterfaceRepository.findById(clienteDto.getId())).thenReturn(Optional.ofNullable(clienteEntidad));

        when(clienteInterfaceMapper.toClienteDto(clienteEntidad)).thenReturn(clienteDto);

        ClienteDto clienteDtoActual = clienteService.findById(clienteDto.getId());

        assertEquals(clienteDto, clienteDtoActual);
    }

    @Test
    void findByIdentificacion() throws Exception {
        when(clienteInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(Optional.of(clienteEntidad));

        when(clienteInterfaceMapper.toClienteDto(clienteEntidad)).thenReturn(clienteDto);

        ClienteDto clienteActual = clienteService.findByIdentificacion(clienteDto.getIdentificacion());

        assertEquals(clienteDto, clienteActual);

        when(clienteInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(clienteService.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        when(clienteInterfaceService.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        ClienteDto clienteDto1 = clienteUseCase.buscarPorIdentificacion(clienteDto.getIdentificacion());

        assertEquals(clienteDto, clienteDto1);
    }

    @Test
    void ExistByIdentificacionException() throws Exception {
        when(clienteInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(false);

        assertThrows(RequestException.class, () -> clienteUseCase.buscarPorIdentificacion(clienteDto.getIdentificacion()));
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

        when(clienteService.findAll()).thenReturn(clienteDtoList);

        when(clienteInterfaceService.findAll()).thenReturn(clienteDtoList);

        List<ClienteDto> clienteDtoListActual = clienteUseCase.listar();

        assertEquals(clienteDtoList, clienteDtoListActual);
    }

    @Test
    void findAllException() throws Exception {
        List<ClienteDto> clienteDtoList = new ArrayList<>();

        List<ClienteEntidad> clienteEntidadList = new ArrayList<>();

        when(clienteInterfaceService.findAll()).thenReturn(clienteDtoList);

        assertThrows(LogicException.class, () -> clienteUseCase.listar());
    }

    @Test
    void findByIdentificacionFile() throws Exception {
        when(clienteInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(Optional.of(clienteEntidad));

        when(clienteInterfaceMapper.toClienteDto(clienteEntidad)).thenReturn(clienteDto);

        when(fileImagenServiceClient.findByNumeroIdentificacion(clienteDto.getIdentificacion())).thenReturn(fileImagenDto);

        ClienteFileDto clienteActual = clienteService.findByIdentificacionFile(clienteDto.getIdentificacion());

        assertEquals(clienteFileDto, clienteActual);

        when(clienteInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(clienteInterfaceService.findByIdentificacionFile(clienteDto.getIdentificacion())).thenReturn(clienteFileDto);

        ClienteFileDto clienteDto1 = clienteUseCase.buscarPorIdentificacionFile(clienteDto.getIdentificacion());

        assertEquals(clienteFileDto, clienteDto1);
    }

    @Test
    void findByIdentificacionFilenull() throws Exception {
        ClienteFileDto clienteFileDto = DataTest.clienteFileDtonull();

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

        when(clienteService.findByAge(18)).thenReturn(clienteDtoList);

        when(clienteInterfaceService.findByAge(18)).thenReturn(clienteDtoList);

        List<ClienteDto> clienteDtoListActual = clienteUseCase.listarPorEdadMayor(18);

        assertEquals(clienteDtoList, clienteDtoListActual);
    }

    @Test
    void findByAgeException() throws Exception {

        List<ClienteDto> clienteDtoList = new ArrayList<>();

        List<ClienteEntidad> clienteEntidadList = new ArrayList<>();

        when(clienteInterfaceService.findByAge(18)).thenReturn(clienteDtoList);

        assertThrows(LogicException.class, () -> clienteUseCase.listarPorEdadMayor(18));
    }

    @Test
    void save() throws Exception {
        when(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(clienteDto.getTipoDocumento())).thenReturn(Optional.ofNullable(tipoDocumentoEntidad));

        clienteInterfaceRepository.save(clienteEntidad);

        boolean response = clienteService.save(clienteDto);

        assertTrue(response);

        when(clienteInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(false);

        when(tipoDocumentoUseCase.existsByTipoDocumento(clienteDto.getTipoDocumento())).thenReturn(true);

        when(clienteService.save(clienteDto)).thenReturn(true);

        when(clienteInterfaceService.save(clienteDto)).thenReturn(true);

        boolean responseActual = clienteUseCase.guardar(clienteDto);

        assertTrue(responseActual);
    }

    @Test
    void saveException() throws Exception {
        when(clienteInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        assertThrows(RequestException.class, () -> clienteUseCase.guardar(clienteDto));
    }

    @Test
    void update() throws Exception {
        when(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(clienteDto.getTipoDocumento())).thenReturn(Optional.ofNullable(tipoDocumentoEntidad));

        when(clienteInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(Optional.ofNullable(clienteEntidad));

        clienteInterfaceRepository.save(clienteEntidad);

        boolean response = clienteService.update(clienteDto);

        assertTrue(response);

        when(clienteInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(tipoDocumentoUseCase.existsByTipoDocumento(clienteDto.getTipoDocumento())).thenReturn(true);

        when(clienteService.update(clienteDto)).thenReturn(true);

        when(clienteInterfaceService.update(clienteDto)).thenReturn(true);

        boolean responseActual = clienteUseCase.actualizar(clienteDto);

        assertTrue(responseActual);
    }

    @Test
    void delete() throws Exception {

        when(fileImagenServiceClient.findByNumeroIdentificacion(clienteDto.getIdentificacion())).thenReturn(fileImagenDto);

        when(fileImagenServiceClient.delete(clienteDto.getIdentificacion())).thenReturn(true);

        when(clienteInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(Optional.ofNullable(clienteEntidad));

        clienteInterfaceRepository.delete(clienteEntidad);

        boolean response = clienteService.delete(clienteDto.getIdentificacion());

        assertTrue(response);

        when(clienteInterfaceService.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(clienteUseCase.existsByIdentificacion(clienteDto.getIdentificacion())).thenReturn(true);

        when(clienteInterfaceService.delete(clienteDto.getIdentificacion())).thenReturn(true);

        boolean responseActual = clienteUseCase.eliminar(clienteDto.getIdentificacion());

        assertTrue(responseActual);
    }

    @Test
    void deleteException() throws Exception {
        when(fileImagenServiceClient.findByNumeroIdentificacion(clienteDto.getIdentificacion())).thenReturn(fileImagenDto = null);

        when(clienteInterfaceRepository.findByIdentificacion(clienteDto.getIdentificacion())).thenReturn(Optional.ofNullable(clienteEntidad));

        clienteInterfaceRepository.delete(clienteEntidad);

        boolean response = clienteService.delete(clienteDto.getIdentificacion());

        assertTrue(response);

    }

    @Test
    void existsByTipoDocumentoEntidad() throws Exception {
        when(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(clienteDto.getTipoDocumento())).thenReturn(Optional.ofNullable(tipoDocumentoEntidad));

        when(clienteInterfaceRepository.existsByTipoDocumento(tipoDocumentoEntidad.getId())).thenReturn(1);

        boolean response = clienteService.existsByTipoDocumentoEntidad(clienteDto.getTipoDocumento());

        assertTrue(response);
    }

    @Test
    void existsByTipoDocumentoEntidadFalse() throws Exception {
        when(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(clienteDto.getTipoDocumento())).thenReturn(Optional.ofNullable(tipoDocumentoEntidad));

        when(clienteInterfaceRepository.existsByTipoDocumento(tipoDocumentoEntidad.getId())).thenReturn(0);

        boolean response = clienteService.existsByTipoDocumentoEntidad(clienteDto.getTipoDocumento());

        assertFalse(response);
    }

}
