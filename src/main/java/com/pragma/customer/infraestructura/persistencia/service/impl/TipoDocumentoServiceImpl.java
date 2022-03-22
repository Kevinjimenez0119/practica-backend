package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.infraestructura.mappers.TipoDocumentoInterfaceMapper;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import com.pragma.customer.infraestructura.persistencia.repository.TipoDocumentoInterfaceReporsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoDocumentoServiceImpl implements TipoDocumentoInterfaceService {

    @Autowired
    private TipoDocumentoInterfaceReporsitory tipoDocumentoInterfaceReporsitory;

    @Autowired
    private TipoDocumentoInterfaceMapper tipoDocumentoInterfaceMapper;

    @Override
    public boolean save(TipoDocumentoDto tipoDocumentoDto) {
        TipoDocumentoEntidad tipoDocumentoEntidad = tipoDocumentoInterfaceMapper.toTipoDocumentoEntidad(tipoDocumentoDto);
        tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad);
        return true;
    }

    @Override
    public boolean delete(String tipo) {
        Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo);
        tipoDocumentoInterfaceReporsitory.delete(tipoDocumentoEntidad.get());
        return true;
    }

    @Override
    public boolean update(TipoDocumentoDto tipoDocumentoDto) {
        Optional<TipoDocumentoEntidad> tipoDocumentoEntidad = tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipoDocumentoDto.getTipoDocumento());
        tipoDocumentoEntidad.get().setTipoDocumento(tipoDocumentoDto.getTipoDocumento());
        tipoDocumentoInterfaceReporsitory.save(tipoDocumentoEntidad.get());
        return true;
    }

    @Override
    public List<TipoDocumentoDto> findAll() {
        return tipoDocumentoInterfaceMapper.toTipoDocumentoListDto(tipoDocumentoInterfaceReporsitory.findAll());
    }

    @Override
    public TipoDocumentoDto findById(Integer id) {
        return tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoInterfaceReporsitory.findById(id).get());
    }

    @Override
    public TipoDocumentoDto findByTipoDocumento(String tipo) {
        return tipoDocumentoInterfaceMapper.toTipoDocumentoDto(tipoDocumentoInterfaceReporsitory.findByTipoDocumento(tipo).get());
    }

    @Override
    public boolean existsByTipoDocumento(String tipo) {
        return tipoDocumentoInterfaceReporsitory.existsByTipoDocumento(tipo);
    }
}
