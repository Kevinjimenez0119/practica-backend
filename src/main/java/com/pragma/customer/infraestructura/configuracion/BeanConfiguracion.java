package com.pragma.customer.infraestructura.configuracion;

import com.pragma.customer.aplicacion.manjeador.ManejadorCliente;
import com.pragma.customer.aplicacion.manjeador.ManejadorTipoDocumento;
import com.pragma.customer.dominio.service.ClienteInterfaceService;
import com.pragma.customer.dominio.service.TipoDocumentoInterfaceService;
import com.pragma.customer.dominio.useCase.cliente.ClienteUseCase;
import com.pragma.customer.dominio.useCase.tipodocumento.TipoDocumentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguracion {

    //Bean de Cliente
    @Bean
    public ClienteUseCase clienteUseCase(ClienteInterfaceService clienteInterfaceService) {
        return new ClienteUseCase(clienteInterfaceService);
    }

    @Bean
    public ManejadorCliente manejadorCliente(ClienteUseCase clienteUseCase) {
        return new ManejadorCliente(clienteUseCase);
    }

    //Bean de TipoDocumento
    @Bean
    public TipoDocumentoUseCase tipoDocumentoUseCase(TipoDocumentoInterfaceService tipoDocumentoInterfaceService) {
        return new TipoDocumentoUseCase(tipoDocumentoInterfaceService);
    }

    @Bean
    public ManejadorTipoDocumento manejadorTipoDocumento(TipoDocumentoUseCase tipoDocumentoUseCase) {
        return new ManejadorTipoDocumento(tipoDocumentoUseCase);
    }
}
