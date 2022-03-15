package com.pragma.customer.data;

import com.pragma.customer.dominio.modelo.ClienteDto;
import com.pragma.customer.dominio.modelo.ClienteFileDto;
import com.pragma.customer.dominio.modelo.FileImagenDto;
import com.pragma.customer.dominio.modelo.TipoDocumentoDto;
import com.pragma.customer.infraestructura.persistencia.entity.ClienteEntidad;
import com.pragma.customer.infraestructura.persistencia.entity.TipoDocumentoEntidad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTest {

    public static ClienteDto cliente1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ClienteDto(5, "kevin2", "jimenez", "CC", 100505349, 21, "Cucuta", sdf.parse("2001-01-19"));
    }

    public static ClienteEntidad clienteEntidad() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ClienteEntidad(5, "kevin2", "jimenez", tipo1(), 100505349, 21, "Cucuta", sdf.parse("2001-01-19"));
    }

    public static TipoDocumentoEntidad tipo1() {
        return  new TipoDocumentoEntidad(1,"CC");
    }

    public static FileImagenDto fileImagenDto() {
        return new FileImagenDto("base", 100505349, "prueba.jpg", "image/jpg");
    }

    public static ClienteFileDto clienteFileDto() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ClienteFileDto(5,"kevin2", "jimenez", "CC", 100505349, 21, "Cucuta", sdf.parse("2001-01-19"),fileImagenDto());
    }

    public static ClienteFileDto clienteFileDtonull() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new ClienteFileDto(5,"kevin2", "jimenez", "CC", 100505349, 21, "Cucuta", sdf.parse("2001-01-19"),new FileImagenDto("",100505349,"",""));
    }
}
