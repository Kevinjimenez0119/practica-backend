package com.pragma.customer.dominio.useCase.tipodocumento;

import com.pragma.customer.dominio.modelo.TipoDocumento;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoDocumentoUseCase {

    private final TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    public void guardar(TipoDocumento tipoDocumento){
        tipoDocumentoInterfaceService.save(tipoDocumento);
    }

    public void actualizar(TipoDocumento tipoDocumento){
        tipoDocumentoInterfaceService.update(tipoDocumento);
    }

    public void eliminar(String tipo) {
        tipoDocumentoInterfaceService.delete(tipo);
    }

    public List<TipoDocumento> listar() {
        return tipoDocumentoInterfaceService.findAll();
    }

    public TipoDocumento buscarPorId(Integer id) {
        return tipoDocumentoInterfaceService.findById(id);
    }

    public TipoDocumento buscarPorIdentificacion(Integer identificacion) {
        return tipoDocumentoInterfaceService.findById(identificacion);
    }

    public boolean existeTipo(String tipo) {
        return tipoDocumentoInterfaceService.existsByTipoDocumento(tipo);
    }

    public TipoDocumento buscarPorTipo(String tipo) {
        return tipoDocumentoInterfaceService.findByTipoDocumento(tipo);
    }
}
