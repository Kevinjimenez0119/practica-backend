package com.pragma.customer.aplicacion.manjeador;

import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ManejadorTipoDocumento {

    private final TipoDocumentoUseCase tipoDocumentoUseCase;

    public void guardar(TipoDocumentoEntidad tipoDocumentoEntidad){
        tipoDocumentoUseCase.guardar(tipoDocumentoEntidad);
    }

    public void eliminar(Integer id) {
        tipoDocumentoUseCase.eliminar(id);
    }

    public List<TipoDocumentoEntidad> listar() {
        return tipoDocumentoUseCase.listar();
    }

    public TipoDocumentoEntidad buscarPorId(Integer id) {
        return tipoDocumentoUseCase.buscarPorId(id);
    }

    public TipoDocumentoEntidad buscarPorIdentificacion(Integer identificacion) {
        return tipoDocumentoUseCase.buscarPorIdentificacion(identificacion);
    }

    public boolean existeTipo(String tipo) {
        return tipoDocumentoUseCase.existeTipo(tipo);
    }

    public TipoDocumentoEntidad buscarPorTipo(String tipo) {
        return tipoDocumentoUseCase.buscarPorTipo(tipo);
    }
}
