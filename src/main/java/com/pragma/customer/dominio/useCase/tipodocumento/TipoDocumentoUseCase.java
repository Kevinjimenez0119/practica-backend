package com.pragma.customer.dominio.useCase.tipodocumento;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoDocumentoUseCase {

    private final TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    public void guardar(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        tipoDocumentoInterfaceService.save(tipoDocumentoDto);
    }

    public void actualizar(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        tipoDocumentoInterfaceService.update(tipoDocumentoDto);
    }

    public void eliminar(String tipo) throws Exception {
        tipoDocumentoInterfaceService.delete(tipo);
    }

    public List<TipoDocumentoDto> listar() throws Exception {
        return tipoDocumentoInterfaceService.findAll();
    }

    public TipoDocumentoDto buscarPorId(Integer id) {
        return tipoDocumentoInterfaceService.findById(id);
    }

    public TipoDocumentoDto buscarPorIdentificacion(Integer identificacion) {
        return tipoDocumentoInterfaceService.findById(identificacion);
    }

    public boolean existeTipo(String tipo) throws Exception {
        return tipoDocumentoInterfaceService.existsByTipoDocumento(tipo);
    }

    public TipoDocumentoDto buscarPorTipo(String tipo) throws Exception {
        return tipoDocumentoInterfaceService.findByTipoDocumento(tipo);
    }
}
