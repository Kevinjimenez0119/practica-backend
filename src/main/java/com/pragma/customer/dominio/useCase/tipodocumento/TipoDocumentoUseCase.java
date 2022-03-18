package com.pragma.customer.dominio.useCase.tipodocumento;

import com.pragma.customer.aplicacion.utils.ErrorsUtils;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
import com.pragma.customer.infraestructura.exceptions.LogicException;
import com.pragma.customer.infraestructura.exceptions.RequestException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoDocumentoUseCase {

    private final TipoDocumentoInterfaceService tipoDocumentoInterfaceService;

    private final ClienteUseCase clienteUseCase;

    public boolean guardar(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        if (!tipoDocumentoInterfaceService.existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento())) {
            return tipoDocumentoInterfaceService.save(tipoDocumentoDto);
        } else {
            throw new RequestException(400, ErrorsUtils.tipoIdentificacionRegistrada(tipoDocumentoDto.getTipoDocumento()));
        }
    }

    public boolean actualizar(TipoDocumentoDto tipoDocumentoDto) throws Exception {
        existsByTipoDocumento(tipoDocumentoDto.getTipoDocumento());
        return tipoDocumentoInterfaceService.update(tipoDocumentoDto);
    }

    public boolean eliminar(String tipo) throws Exception {
        existsByTipoDocumento(tipo);
        if(!clienteUseCase.existsByTipoDocumentoEntidad(tipo)) {
            return tipoDocumentoInterfaceService.delete(tipo);
        } else {
            throw new RequestException(400, "el tipo de documento lo tienen algunos clientes");
        }
    }

    public List<TipoDocumentoDto> listar() throws Exception {
        List<TipoDocumentoDto> tipoDocumentoDtoList = tipoDocumentoInterfaceService.findAll();
        if(tipoDocumentoDtoList.isEmpty()) {
            throw new LogicException(204, ErrorsUtils.sinRegistros());
        }
        return tipoDocumentoDtoList;
    }

    public TipoDocumentoDto buscarPorTipo(String tipo) throws Exception {
        existsByTipoDocumento(tipo);
        return tipoDocumentoInterfaceService.findByTipoDocumento(tipo);
    }

    public boolean existsByTipoDocumento(String tipo) throws Exception {
        if(tipoDocumentoInterfaceService.existsByTipoDocumento(tipo)) {
            return true;
        } else {
            throw new RequestException(404, ErrorsUtils.tipoIdentificacionNoRegistrada(tipo));
        }
    }
}
