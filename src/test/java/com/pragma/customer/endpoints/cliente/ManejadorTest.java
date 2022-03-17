package com.pragma.customer.endpoints.cliente;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.data.DataTest;
import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.modelo.FileImagenDto;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ManejadorTest {

    @InjectMocks
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
        when(clienteUseCase.buscarPorIdentificacion(clienteDto.getIdentificacion())).thenReturn(clienteDto);

        ClienteDto clienteDtoActual = manejadorCliente.buscarPorIdentificacion(fileImagenDto.getIdentificacion());

        assertEquals(clienteDto, clienteDtoActual);
    }

    @Test
    void findAll() throws Exception {
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        clienteDtoList.add(clienteDto);

        when(clienteUseCase.listar()).thenReturn(clienteDtoList);

        List<ClienteDto> clienteDtoListActual = manejadorCliente.listar();

        assertEquals(clienteDtoList, clienteDtoListActual);
    }
    @Test
    void save() throws Exception {
        when(clienteUseCase.guardar(clienteDto)).thenReturn(true);

        boolean response = manejadorCliente.guardar(clienteDto);

        assertEquals(true, response);
    }

    @Test
    void delete() throws Exception {
        when(clienteUseCase.eliminar(clienteDto.getIdentificacion())).thenReturn(true);

        boolean response = manejadorCliente.eliminar(fileImagenDto.getIdentificacion());

        assertEquals(true, response);
    }

    @Test
    void update() throws Exception {
        when(clienteUseCase.actualizar(clienteDto)).thenReturn(true);

        boolean response = manejadorCliente.actualizar(clienteDto);

        assertEquals(true, response);
    }

    @Test
    void findByIdentificacionFile() throws Exception {
        when(clienteUseCase.buscarPorIdentificacionFile(clienteDto.getIdentificacion())).thenReturn(clienteFileDto);

        ClienteFileDto clienteFileDtoActual = manejadorCliente.buscarPorIdentificacionFile(clienteDto.getIdentificacion());

        assertEquals(clienteFileDto, clienteFileDtoActual);

    }

    @Test
    void findByAge() throws Exception {
        List<ClienteDto> clienteDtoList = new ArrayList<>();
        clienteDtoList.add(clienteDto);

        when(clienteUseCase.listarPorEdadMayor(19)).thenReturn(clienteDtoList);

        List<ClienteDto> clienteDtoListActual = manejadorCliente.listarPorEdadMayor(19);

        assertEquals(clienteDtoList, clienteDtoListActual);
    }

    @Test
    void Age() throws Exception {
        when(clienteUseCase.getAgeByDate(clienteDto.getFechaNacimiento())).thenReturn(21);

        Integer edad = manejadorCliente.edadPorFecha(clienteDto.getFechaNacimiento());

        assertEquals(21, edad);
    }

    @Test
    void birthDay() throws Exception {
        LocalDate localDate = LocalDate.now();
        when(clienteUseCase.getBirthdayDate(clienteDto.getFechaNacimiento())).thenReturn(LocalDate.now());

        LocalDate localActual = manejadorCliente.birthDay(clienteDto.getFechaNacimiento());

        assertEquals(localDate, localActual);
    }
}
