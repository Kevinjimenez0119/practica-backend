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

    public void guardar(ClienteDto cliente) throws Exception {
        clienteInterfaceService.save(cliente);
    }

    public void actualizar(ClienteDto cliente) throws Exception {
        clienteInterfaceService.update(cliente);
    }

    public void eliminar(Integer id) throws Exception {
        clienteInterfaceService.delete(id);
    }

    public List<ClienteDto> listar() throws Exception {
        return clienteInterfaceService.findAll();
    }

    public ClienteDto buscarPorId(Integer id) {
        return clienteInterfaceService.findById(id);
    }

    public ClienteDto buscarPorIdentificacion(Integer identificacion) throws Exception {
        return clienteInterfaceService.findByIdentificacion(identificacion);
    }

    public ClienteFileDto buscarPorIdentificacionFile(Integer identificacion) throws Exception {
        return clienteInterfaceService.findByIdentificacionFile(identificacion);
    }

    public List<ClienteDto> listarPorEdadMayor(Integer edad) throws Exception {
        return clienteInterfaceService.findByAge(edad);
    }

    public boolean existeCliente(Integer identificacion) throws Exception {
        return clienteInterfaceService.existsByIdentificacion(identificacion);
    }

    public Page<ClienteDto> listarPag(Pageable pageable) throws Exception {
        return clienteInterfaceService.findAllPag(pageable);
    }

    public LocalDate birthDay(Date fechaNac) {
        return clienteInterfaceService.getBirthdayDate(fechaNac);
    }

    public  Integer edadPorFecha(Date fechaNac) {
        return clienteInterfaceService.getAgeByDate(fechaNac);
    }

    public boolean buscarPorTipoDeDocumento(String tipo) throws Exception {
        return clienteInterfaceService.existsByTipoDocumentoEntidad(tipo);
    }
}
