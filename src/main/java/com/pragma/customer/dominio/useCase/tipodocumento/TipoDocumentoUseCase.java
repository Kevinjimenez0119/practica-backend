package com.pragma.customer.dominio.useCase.tipodocumento;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoDocumentoUseCase {

    private final TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    public void guardar(TipoDocumentoDto tipoDocumentoDto){
        tipoDocumentoInterfaceService.save(tipoDocumentoDto);
    }

    public void actualizar(TipoDocumentoDto tipoDocumentoDto){
        tipoDocumentoInterfaceService.update(tipoDocumentoDto);
    }

    public void eliminar(String tipo) {
        tipoDocumentoInterfaceService.delete(tipo);
    }

    public List<TipoDocumentoDto> listar() {
        return tipoDocumentoInterfaceService.findAll();
    }

    public TipoDocumentoDto buscarPorId(Integer id) {
        return tipoDocumentoInterfaceService.findById(id);
    }

    public TipoDocumentoDto buscarPorIdentificacion(Integer identificacion) {
        return tipoDocumentoInterfaceService.findById(identificacion);
    }

    public boolean existeTipo(String tipo) {
        return tipoDocumentoInterfaceService.existsByTipoDocumento(tipo);
    }

    public TipoDocumentoDto buscarPorTipo(String tipo) {
        return tipoDocumentoInterfaceService.findByTipoDocumento(tipo);
    }
}
