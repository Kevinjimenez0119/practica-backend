package com.pragma.customer.infraestructura.mappers;

import com.pragma.customer.dominio.modelo.TipoDocumento;
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
    TipoDocumento toTipoDocumentoDto(TipoDocumentoEntidad tipoDocumentoEntidad);

    List<TipoDocumento> toTipoDocumentoListDto(List<TipoDocumentoEntidad> tipoDocumentoEntidad);

    @InheritInverseConfiguration
    TipoDocumentoEntidad toTipoDocumentoEntidad(TipoDocumento tipoDocumento);

    @InheritInverseConfiguration
    List<TipoDocumentoEntidad> toTipoDocumentoListEntidad(List<TipoDocumento> tipoDocumento);
}
