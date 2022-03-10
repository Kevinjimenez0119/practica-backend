package com.pragma.customer.aplicacion.manjeador;

import com.pragma.customer.dominio.modelo.Cliente;
import com.pragma.customer.dominio.modelo.ClienteFile;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ManejadorCliente {

    private final ClienteUseCase clienteUseCase;

    public void guardar(Cliente cliente) {
        clienteUseCase.guardar(cliente);
    }

    public void actualizar(Cliente cliente) {
        clienteUseCase.actualizar(cliente);
    }

    public void eliminar(Integer id) {
        clienteUseCase.eliminar(id);
    }

    public List<Cliente> listar() {
        return clienteUseCase.listar();
    }

    public Cliente buscarPorId(Integer id) {
        return clienteUseCase.buscarPorId(id);
    }

    public Cliente buscarPorIdentificacion(Integer identificacion) {
        return clienteUseCase.buscarPorIdentificacion(identificacion);
    }

    public ClienteFile buscarPorIdentificacionFile(Integer identificacion) {
        return clienteUseCase.buscarPorIdentificacionFile(identificacion);
    }

    public List<Cliente> listarPorEdadMayor(Integer edad) {
        return clienteUseCase.listarPorEdadMayor(edad);
    }

    public boolean existeCliente(Integer identificacion) {
        return clienteUseCase.existeCliente(identificacion);
    }

    public Page<Cliente> listarPag(Pageable pageable) {
        return clienteUseCase.listarPag(pageable);
    }

    public LocalDate birthDay(Date fechaNac) {
        return clienteUseCase.birthDay(fechaNac);
    }

    public Integer edadPorFecha(Date fechaNac) {
        return clienteUseCase.edadPorFecha(fechaNac);
    }
}
