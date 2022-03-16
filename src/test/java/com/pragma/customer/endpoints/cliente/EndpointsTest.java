package com.pragma.customer.endpoints.cliente;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.data.DataTest;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.modelo.FileImagenDto;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
import com.pragma.customer.infraestructura.endpoint.clientes.*;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
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
    EndpointActualizarCliente endpointActualizarCliente;

    @InjectMocks
    EndpointObtenerClienteConFile endpointObtenerClienteConFile;

    @InjectMocks
    EndpointBuscarPorSoloIdentificacion endpointBuscarPorSoloIdentificacion;

    @InjectMocks
    EndpointBuscarPorTipoIdentificacion endpointBuscarPorTipoIdentificacion;

    @InjectMocks
    EndpointEliminarCliente endpointEliminarCliente;

    @InjectMocks
    EndpointGuardarCliente endpointGuardarCliente;

    @InjectMocks
    EndpointListarClientes endpointListarClientes;

    @InjectMocks
    EndpointListarPorEdad endpointListarPorEdad;

    @Mock
    ManejadorCliente manejadorCliente;

    @Mock
    ClienteUseCase clienteUseCase;

    ClienteDto clienteDto;
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
        ResponseEntity<?> fileResponseEntity = new ResponseEntity(clienteDto, HttpStatus.OK);

        when(clienteUseCase.buscarPorIdentificacion(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        when(manejadorCliente.buscarPorIdentificacion(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        ResponseEntity<?> response = endpointBuscarPorSoloIdentificacion.obtenerPorIdentificacion(clienteDto.getIdentificacion());

        ResponseEntity<?> response2 = endpointBuscarPorTipoIdentificacion.obtenerPorIdentificacion(clienteDto.getTipoDocumento(),clienteDto.getIdentificacion());

        assertEquals(fileResponseEntity, response);

        assertEquals(fileResponseEntity, response2);
    }

    @Test
    void findAll() throws Exception {
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        clienteDtoList.add(clienteDto);

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(clienteDtoList, HttpStatus.OK);

        when(clienteUseCase.listar()).thenReturn(clienteDtoList);

        when(manejadorCliente.listar()).thenReturn(clienteDtoList);

        ResponseEntity<?> response = endpointListarClientes.listarClientes();

        assertEquals(fileResponseEntity, response);
    }

    @Test
    void findByAge() throws Exception {
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        clienteDtoList.add(clienteDto);

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(clienteDtoList, HttpStatus.OK);

        when(clienteUseCase.listarPorEdadMayor(19)).thenReturn(clienteDtoList);

        when(manejadorCliente.listarPorEdadMayor(19)).thenReturn(clienteDtoList);

        ResponseEntity<?> response = endpointListarPorEdad.obtenerPorEdadMayorIgual(19);

        assertEquals(fileResponseEntity, response);
    }

    @Test
    void save() throws Exception {
        ResponseEntity<?> fileResponseEntity = new ResponseEntity(HttpStatus.CREATED);

        ResponseEntity<?> response = endpointGuardarCliente.guardarCliente(clienteDto);

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    void delete() throws Exception {

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(HttpStatus.OK);

        ResponseEntity<?> response = endpointEliminarCliente.eliminar(clienteDto.getIdentificacion());

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    void update() throws Exception {

        ResponseEntity<?> fileResponseEntity = new ResponseEntity(HttpStatus.OK);

        ResponseEntity<?> response = endpointActualizarCliente.actualizar(clienteDto);

        assertEquals(fileResponseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    void findByIdentificacionFile() throws Exception {
        ResponseEntity<?> fileResponseEntity = new ResponseEntity(clienteFileDto, HttpStatus.OK);

        when(clienteUseCase.buscarPorIdentificacionFile(clienteDto.getIdentificacion())).thenReturn(clienteFileDto);

        when(manejadorCliente.buscarPorIdentificacionFile(clienteDto.getIdentificacion())).thenReturn(clienteFileDto);

        ResponseEntity<?> response = endpointObtenerClienteConFile.obtenerPorIdentificacion(clienteDto.getTipoDocumento(), clienteDto.getIdentificacion());

        assertEquals(fileResponseEntity, response);

    }
}
