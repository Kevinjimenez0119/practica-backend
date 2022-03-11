package com.pragma.customer.infraestructura.mappers;

import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoDocumentoInterfaceMapper {


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "tipoDocumento", target = "tipoDocumento")
    })
    TipoDocumentoDto toTipoDocumentoDto(TipoDocumentoEntidad tipoDocumentoEntidad);

    List<TipoDocumentoDto> toTipoDocumentoListDto(List<TipoDocumentoEntidad> tipoDocumentoEntidad);

    @InheritInverseConfiguration
    TipoDocumentoEntidad toTipoDocumentoEntidad(TipoDocumentoDto tipoDocumentoDto);

    @InheritInverseConfiguration
    List<TipoDocumentoEntidad> toTipoDocumentoListEntidad(List<TipoDocumentoDto> tipoDocumentoDto);
}
