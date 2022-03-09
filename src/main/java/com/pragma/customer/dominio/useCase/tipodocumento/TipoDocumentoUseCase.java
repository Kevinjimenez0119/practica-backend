package com.pragma.customer.dominio.useCase.tipodocumento;

import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoDocumentoUseCase {

    private final TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    public void guardar(TipoDocumentoEntidad tipoDocumentoEntidad){
        tipoDocumentoInterfaceService.save(tipoDocumentoEntidad);
    }

    public void eliminar(Integer id) {
        tipoDocumentoInterfaceService.delete(id);
    }

    public List<TipoDocumentoEntidad> listar() {
        return tipoDocumentoInterfaceService.findAll();
    }

    public TipoDocumentoEntidad buscarPorId(Integer id) {
        return tipoDocumentoInterfaceService.findById(id).get();
    }

    public TipoDocumentoEntidad buscarPorIdentificacion(Integer identificacion) {
        return tipoDocumentoInterfaceService.findById(identificacion).get();
    }

    public boolean existeTipo(String tipo) {
        return tipoDocumentoInterfaceService.existsByTipoDocumento(tipo);
    }

    public TipoDocumentoEntidad buscarPorTipo(String tipo) {
        return tipoDocumentoInterfaceService.findByTipoDocumento(tipo).get();
    }
}
