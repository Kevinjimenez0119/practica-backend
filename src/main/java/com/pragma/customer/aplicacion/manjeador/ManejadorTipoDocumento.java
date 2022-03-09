package com.pragma.customer.aplicacion.manjeador;

import com.pragma.customer.dominio.modelo.TipoDocumento;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ManejadorTipoDocumento {

    private final TipoDocumentoUseCase tipoDocumentoUseCase;

    public void guardar(TipoDocumento tipoDocumentoEntidad){
        tipoDocumentoUseCase.guardar(tipoDocumentoEntidad);
    }

    public void actualizar(TipoDocumento tipoDocumento){
        tipoDocumentoUseCase.actualizar(tipoDocumento);
    }

    public void eliminar(String tipo) {
        tipoDocumentoUseCase.eliminar(tipo);
    }

    public List<TipoDocumento> listar() {
        return tipoDocumentoUseCase.listar();
    }

    public TipoDocumento buscarPorId(Integer id) {
        return tipoDocumentoUseCase.buscarPorId(id);
    }

    public TipoDocumento buscarPorIdentificacion(Integer identificacion) {
        return tipoDocumentoUseCase.buscarPorIdentificacion(identificacion);
    }

    public boolean existeTipo(String tipo) {
        return tipoDocumentoUseCase.existeTipo(tipo);
    }

    public TipoDocumento buscarPorTipo(String tipo) {
        return tipoDocumentoUseCase.buscarPorTipo(tipo);
    }
}
