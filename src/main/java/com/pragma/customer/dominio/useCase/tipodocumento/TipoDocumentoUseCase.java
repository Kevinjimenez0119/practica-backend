package com.pragma.customer.dominio.useCase.tipodocumento;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoDocumentoUseCase {

    private final TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    public boolean guardar(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        return tipoDocumentoInterfaceService.save(tipoDocumentoDto);
    }

    public boolean actualizar(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        return tipoDocumentoInterfaceService.update(tipoDocumentoDto);
    }

    public boolean eliminar(String tipo) throws Exception {
        return tipoDocumentoInterfaceService.delete(tipo);
    }

    public List<TipoDocumentoDto> listar() throws Exception {
        return tipoDocumentoInterfaceService.findAll();
    }

    public TipoDocumentoDto buscarPorTipo(String tipo) throws Exception {
        return tipoDocumentoInterfaceService.findByTipoDocumento(tipo);
    }
}
