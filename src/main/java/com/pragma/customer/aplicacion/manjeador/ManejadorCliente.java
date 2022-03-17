package com.pragma.customer.aplicacion.manjeador;

import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ManejadorCliente {

    private final ClienteUseCase clienteUseCase;

    public boolean guardar(ClienteDto cliente) throws Exception {
        return clienteUseCase.guardar(cliente);
    }

    public boolean actualizar(ClienteDto cliente) throws Exception {
        return clienteUseCase.actualizar(cliente);
    }

    public boolean eliminar(Integer id) throws Exception {
        return clienteUseCase.eliminar(id);
    }

    public List<ClienteDto> listar() throws Exception {
        return clienteUseCase.listar();
    }

    public ClienteDto buscarPorIdentificacion(Integer identificacion) throws Exception {
        return clienteUseCase.buscarPorIdentificacion(identificacion);
    }

    public ClienteFileDto buscarPorIdentificacionFile(Integer identificacion) throws Exception {
        return clienteUseCase.buscarPorIdentificacionFile(identificacion);
    }

    public List<ClienteDto> listarPorEdadMayor(Integer edad) throws Exception {
        return clienteUseCase.listarPorEdadMayor(edad);
    }


    public Page<ClienteDto> listarPag(Pageable pageable) throws Exception {
        return clienteUseCase.listarPag(pageable);
    }

    public LocalDate birthDay(Date fechaNac) {
        return clienteUseCase.getBirthdayDate(fechaNac);
    }

    public Integer edadPorFecha(Date fechaNac) {
        return clienteUseCase.getAgeByDate(fechaNac);
    }
}
