package com.pragma.customer.aplicacion.manjeador;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ManejadorTipoDocumento {

    private final TipoDocumentoUseCase tipoDocumentoUseCase;

    public boolean guardar(TipoDocumentoDto tipoDocumentoDtoEntidad) throws Exception {
        return tipoDocumentoUseCase.guardar(tipoDocumentoDtoEntidad);
    }

    public boolean actualizar(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        return tipoDocumentoUseCase.actualizar(tipoDocumentoDto);
    }

    public boolean eliminar(String tipo) throws Exception {
        return tipoDocumentoUseCase.eliminar(tipo);
    }

    public List<TipoDocumentoDto> listar() throws Exception {
        return tipoDocumentoUseCase.listar();
    }

    public TipoDocumentoDto buscarPorTipo(String tipo) throws Exception {
        return tipoDocumentoUseCase.buscarPorTipo(tipo);
    }
}
