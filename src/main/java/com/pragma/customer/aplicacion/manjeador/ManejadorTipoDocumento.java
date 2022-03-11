package com.pragma.customer.aplicacion.manjeador;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ManejadorTipoDocumento {

    private final TipoDocumentoUseCase tipoDocumentoUseCase;

    public void guardar(TipoDocumentoDto tipoDocumentoDtoEntidad){
        tipoDocumentoUseCase.guardar(tipoDocumentoDtoEntidad);
    }

    public void actualizar(TipoDocumentoDto tipoDocumentoDto){
        tipoDocumentoUseCase.actualizar(tipoDocumentoDto);
    }

    public void eliminar(String tipo) {
        tipoDocumentoUseCase.eliminar(tipo);
    }

    public List<TipoDocumentoDto> listar() {
        return tipoDocumentoUseCase.listar();
    }

    public TipoDocumentoDto buscarPorId(Integer id) {
        return tipoDocumentoUseCase.buscarPorId(id);
    }

    public TipoDocumentoDto buscarPorIdentificacion(Integer identificacion) {
        return tipoDocumentoUseCase.buscarPorIdentificacion(identificacion);
    }

    public boolean existeTipo(String tipo) {
        return tipoDocumentoUseCase.existeTipo(tipo);
    }

    public TipoDocumentoDto buscarPorTipo(String tipo) {
        return tipoDocumentoUseCase.buscarPorTipo(tipo);
    }
}
