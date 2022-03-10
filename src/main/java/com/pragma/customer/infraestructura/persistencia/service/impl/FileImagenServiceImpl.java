package com.pragma.customer.infraestructura.persistencia.service.impl;

import com.pragma.customer.dominio.modelo.FileImagen;
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

    @Override
    public boolean delete(Integer identificacion) {
        ResponseEntity<Map<String, Object>> clienteResponseEntity = fileImagenInterfaceClient.findByNumeroIdentificacion(identificacion);
        if (clienteResponseEntity.getStatusCodeValue() != 200) {
            return false;
        }
        return true;
    }

    @Override
    public FileImagen findByNumeroIdentificacion(Integer identificacion){
        ResponseEntity<Map<String, Object>> clienteResponseEntity = fileImagenInterfaceClient.findByNumeroIdentificacion(identificacion);
        if (clienteResponseEntity.getStatusCodeValue() != 200) {
            return null;
        }
        FileImagen fileImagen = maptoFotocliente(clienteResponseEntity.getBody());
        return fileImagen;
    }

    private FileImagen maptoFotocliente(Map<String, Object> fileImagenmap)
    {
        FileImagen fileImagen = FileImagen.builder()
                .fileName(fileImagenmap.get("fileName").toString())
                .base64(fileImagenmap.get("base64").toString())
                .fileType(fileImagenmap.get("fileType").toString())
                .identificacion(Integer.parseInt(fileImagenmap.get("identificacion").toString()))
                .build();
        return fileImagen;
    }
}
