package com.pragma.customer.infraestructura.clientefeign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component
@FeignClient(name = "FotoClient", url = "http://localhost:8888/api/files")
public interface FileImagenInterfaceClient {

    @GetMapping(value = "/identificacion/{numero}")
    ResponseEntity<Map<String, Object>> findByNumeroIdentificacion(@PathVariable Integer numero);

    @DeleteMapping(value = "/identificacion/{numero}")
    @Headers("Content-Type: application/json")
    ResponseEntity<Map<String, Object>> delete(@PathVariable Integer numero);
}
