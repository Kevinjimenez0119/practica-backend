package com.pragma.customer.infraestructura.mappers;

import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteInterfaceMapper {

    TipoDocumentoInterfaceMapper TIPO_DOCUMENTO_INTERFACE_MAPPER = Mappers.getMapper(TipoDocumentoInterfaceMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nombres", target = "nombres"),
            @Mapping(source = "apellidos", target = "apellidos"),
            @Mapping(source = "edad", target = "edad"),
            @Mapping(source = "fechaNacimiento", target = "fechaNacimiento"),
            @Mapping(source = "ciudadNacimiento", target = "ciudadNacimiento"),
            @Mapping(source = "identificacion", target = "identificacion"),
            @Mapping(source = "tipoDocumentoEntidad.tipoDocumento", target = "tipoDocumento")
    })
    ClienteDto toClienteDto(ClienteEntidad clienteEntidad);

    List<ClienteDto> toClienteListDto(List<ClienteEntidad> clienteEntidadList);

    @InheritInverseConfiguration
    ClienteEntidad toClienteEntidad(ClienteDto cliente);

    @InheritInverseConfiguration
    List<ClienteEntidad> toClienteListEntidad(List<ClienteDto> cliente);
}
