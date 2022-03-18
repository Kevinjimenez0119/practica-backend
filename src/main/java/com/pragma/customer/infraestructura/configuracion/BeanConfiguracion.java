package com.pragma.customer.infraestructura.configuracion;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BeanConfiguracion {

    //Bean de Cliente
    @Bean
    public ClienteUseCase clienteUseCase(ClienteInterfaceService clienteInterfaceService, TipoDocumentoUseCase tipoDocumentoUseCase) {
        return new ClienteUseCase(clienteInterfaceService, tipoDocumentoUseCase);
    }

    @Bean
    public ManejadorCliente manejadorCliente(ClienteUseCase clienteUseCase) {
        return new ManejadorCliente(clienteUseCase);
    }

    //Bean de TipoDocumento
    @Bean
    public TipoDocumentoUseCase tipoDocumentoUseCase(TipoDocumentoInterfaceService tipoDocumentoInterfaceService, @Lazy ClienteUseCase clienteUseCase) {
        return new TipoDocumentoUseCase(tipoDocumentoInterfaceService, clienteUseCase);
    }

    @Bean
    public ManejadorTipoDocumento manejadorTipoDocumento(TipoDocumentoUseCase tipoDocumentoUseCase) {
        return new ManejadorTipoDocumento(tipoDocumentoUseCase);
    }
}
