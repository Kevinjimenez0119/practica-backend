package com.pragma.customer.infraestructura.configuracion;

import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguracion {

    private static final String RUTA_PRINCIPAL = "com.pragma.customer.infraestructura.endpoint";


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Cliente REST API")
                        .description("Microservicio de clientes")
                        .termsOfServiceUrl("http://en.wikipedia.org/wiki/Terms_of_service")
                        .contact(new Contact("Kevin Jimenez", "", "kevin.jimenez@pragma.com.co"))
                        .license("Apache License Version 2.0")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                        .version("2.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(RUTA_PRINCIPAL))
                .build();
    }

}
