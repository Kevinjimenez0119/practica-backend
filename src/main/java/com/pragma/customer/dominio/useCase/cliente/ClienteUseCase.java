package com.pragma.customer.dominio.useCase.cliente;

import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ClienteUseCase {

    private final ClienteInterfaceService clienteInterfaceService;

    public void guardar(ClienteDto cliente) {
        clienteInterfaceService.save(cliente);
    }
    public void actualizar(ClienteDto cliente) {
        clienteInterfaceService.update(cliente);
    }

    public void eliminar(Integer id) {
        clienteInterfaceService.delete(id);
    }

    public List<ClienteDto> listar() {
        return clienteInterfaceService.findAll();
    }

    public ClienteDto buscarPorId(Integer id) {
        return clienteInterfaceService.findById(id);
    }

    public ClienteDto buscarPorIdentificacion(Integer identificacion) {
        return clienteInterfaceService.findByIdentificacion(identificacion);
    }

    public ClienteFileDto buscarPorIdentificacionFile(Integer identificacion) {
        return clienteInterfaceService.findByIdentificacionFile(identificacion);
    }

    public List<ClienteDto> listarPorEdadMayor(Integer edad) {
        return clienteInterfaceService.findByAge(edad);
    }

    public boolean existeCliente(Integer identificacion) {
        return clienteInterfaceService.existsByIdentificacion(identificacion);
    }

    public Page<ClienteDto> listarPag(Pageable pageable) {
        return clienteInterfaceService.findAllPag(pageable);
    }

    public LocalDate birthDay(Date fechaNac) {
        return clienteInterfaceService.getBirthdayDate(fechaNac);
    }

    public  Integer edadPorFecha(Date fechaNac) {
        return clienteInterfaceService.getAgeByDate(fechaNac);
    }
}
