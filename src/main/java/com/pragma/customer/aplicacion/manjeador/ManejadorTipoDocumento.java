package com.pragma.customer.aplicacion.manjeador;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ManejadorTipoDocumento {

    private final TipoDocumentoUseCase tipoDocumentoUseCase;

    public void guardar(TipoDocumentoDto tipoDocumentoDtoEntidad) throws Exception {
        tipoDocumentoUseCase.guardar(tipoDocumentoDtoEntidad);
    }

    public void actualizar(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        tipoDocumentoUseCase.actualizar(tipoDocumentoDto);
    }

    public void eliminar(String tipo) throws Exception {
        tipoDocumentoUseCase.eliminar(tipo);
    }

    public List<TipoDocumentoDto> listar() throws Exception {
        return tipoDocumentoUseCase.listar();
    }

    public TipoDocumentoDto buscarPorId(Integer id) {
        return tipoDocumentoUseCase.buscarPorId(id);
    }

    public TipoDocumentoDto buscarPorIdentificacion(Integer identificacion) {
        return tipoDocumentoUseCase.buscarPorIdentificacion(identificacion);
    }

    public boolean existeTipo(String tipo) throws Exception {
        return tipoDocumentoUseCase.existeTipo(tipo);
    }

    public TipoDocumentoDto buscarPorTipo(String tipo) throws Exception {
        return tipoDocumentoUseCase.buscarPorTipo(tipo);
    }
}
