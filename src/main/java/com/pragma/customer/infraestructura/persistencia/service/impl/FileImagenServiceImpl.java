package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pragma.customer.dominio.modelo.FileImagenDto;
import com.pragma.customer.dominio.service.FileImagenServiceClient;
import com.pragma.customer.infraestructura.clientefeign.FileImagenInterfaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class FileImagenServiceImpl implements FileImagenServiceClient {

    @Autowired
    private FileImagenInterfaceClient fileImagenInterfaceClient;

    @HystrixCommand(fallbackMethod = "circuitDelete")
    @Override
    public boolean delete(Integer identificacion) {
        ResponseEntity<Map<String, Object>> clienteResponseEntity = fileImagenInterfaceClient.delete(identificacion);
        return clienteResponseEntity.getStatusCodeValue() == 200;
    }

    public boolean circuitDelete(Integer identificacion) {
        return false;
    }

    @HystrixCommand(fallbackMethod = "circuitFindByIdentificacionFile")
    @Override
    public FileImagenDto findByNumeroIdentificacion(Integer identificacion){
        ResponseEntity<Map<String, Object>> clienteResponseEntity = fileImagenInterfaceClient.findByNumeroIdentificacion(identificacion);
        if (clienteResponseEntity.getStatusCodeValue() != 200) {
            return null;
        }
        return maptoFotocliente(clienteResponseEntity.getBody());
    }

    public FileImagenDto circuitFindByIdentificacionFile(Integer identificacion) {
        return FileImagenDto.builder().fileName("none").build();
    }

    private FileImagenDto maptoFotocliente(Map<String, Object> fileImagenmap)
    {
        return FileImagenDto.builder()
                .fileName(fileImagenmap.get("fileName").toString())
                .base64(fileImagenmap.get("base64").toString())
                .fileType(fileImagenmap.get("fileType").toString())
                .identificacion(Integer.parseInt(fileImagenmap.get("identificacion").toString()))
                .build();
    }
}
