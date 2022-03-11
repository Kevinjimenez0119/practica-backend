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

    public void guardar(ClienteDto cliente) {
        clienteUseCase.guardar(cliente);
    }

    public void actualizar(ClienteDto cliente) {
        clienteUseCase.actualizar(cliente);
    }

    public void eliminar(Integer id) {
        clienteUseCase.eliminar(id);
    }

    public List<ClienteDto> listar() {
        return clienteUseCase.listar();
    }

    public ClienteDto buscarPorId(Integer id) {
        return clienteUseCase.buscarPorId(id);
    }

    public ClienteDto buscarPorIdentificacion(Integer identificacion) {
        return clienteUseCase.buscarPorIdentificacion(identificacion);
    }

    public ClienteFileDto buscarPorIdentificacionFile(Integer identificacion) {
        return clienteUseCase.buscarPorIdentificacionFile(identificacion);
    }

    public List<ClienteDto> listarPorEdadMayor(Integer edad) {
        return clienteUseCase.listarPorEdadMayor(edad);
    }

    public boolean existeCliente(Integer identificacion) {
        return clienteUseCase.existeCliente(identificacion);
    }

    public Page<ClienteDto> listarPag(Pageable pageable) {
        return clienteUseCase.listarPag(pageable);
    }

    public LocalDate birthDay(Date fechaNac) {
        return clienteUseCase.birthDay(fechaNac);
    }

    public Integer edadPorFecha(Date fechaNac) {
        return clienteUseCase.edadPorFecha(fechaNac);
    }
}
