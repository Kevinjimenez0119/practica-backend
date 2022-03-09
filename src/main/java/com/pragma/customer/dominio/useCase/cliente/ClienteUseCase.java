package com.pragma.customer.dominio.useCase.cliente;

import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.modelo.Cliente;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ClienteUseCase {

    private final ClienteInterfaceService clienteInterfaceService;

    public void guardar(Cliente cliente) {
        clienteInterfaceService.save(cliente);
    }
    public void actualizar(Cliente cliente) {
        clienteInterfaceService.update(cliente);
    }

    public void eliminar(Integer id) {
        clienteInterfaceService.delete(id);
    }

    public List<ClienteEntidad> listar() {
        return clienteInterfaceService.findAll();
    }

    public ClienteEntidad buscarPorId(Integer id) {
        return clienteInterfaceService.findById(id).get();
    }

    public ClienteEntidad buscarPorIdentificacion(Integer identificacion) {
        return clienteInterfaceService.findByIdentificacion(identificacion).get();
    }

    public List<ClienteEntidad> listarPorEdadMayor(Integer edad) {
        return clienteInterfaceService.findByAge(edad);
    }

    public boolean existeCliente(Integer identificacion) {
        return clienteInterfaceService.existsByIdentificacion(identificacion);
    }

    public Page<ClienteEntidad> listarPag(Pageable pageable) {
        return clienteInterfaceService.findAllPag(pageable);
    }

    public LocalDate birthDay(Date fechaNac) {
        return clienteInterfaceService.getBirthdayDate(fechaNac);
    }

    public  Integer edadPorFecha(Date fechaNac) {
        return clienteInterfaceService.getAgeByDate(fechaNac);
    }
}
