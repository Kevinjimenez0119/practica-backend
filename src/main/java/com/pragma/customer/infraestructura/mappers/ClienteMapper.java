package com.pragma.customer.infraestructura.mappers;

import com.pragma.customer.dominio.modelo.Cliente;
import com.pragma.customer.dominio.modelo.TipoDocumento;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    TipoDocumentoMapper tipoDocumentoMapper = Mappers.getMapper(TipoDocumentoMapper.class);

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
    Cliente toClienteDto(ClienteEntidad clienteEntidad);

    List<Cliente> toClienteListDto(List<ClienteEntidad> clienteEntidadList);

    @InheritInverseConfiguration
    ClienteEntidad toClienteEntidad(Cliente cliente);

    @InheritInverseConfiguration
    List<ClienteEntidad> toClienteListEntidad(List<Cliente> cliente);
}
