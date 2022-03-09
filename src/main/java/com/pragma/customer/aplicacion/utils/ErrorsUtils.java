package com.pragma.customer.aplicacion.utils;

public class ErrorsUtils {

    private static final String TIPO_IDENTIFICACION_NO_REGISTRADA = "el tipo de documento %s no esta registrada";
    private static final String TIPO_IDENTIFICACION_REGISTRADA = "el tipo de documento %s ya esta registrada";
    private static final String IDENTIFICACION_NO_REGISTRADA = "la identificacion %s no esta registrada";
    private static final String IDENTIFICACION_YA_REGISTRADA = "la identificacion %s ya esta registrada";
    private static final String SIN_CLIENTES_POR_EDAD = "no existen clientes con edad mayor o igual a %d";

    public static String tipoIdentificacionNoRegistrada(String tipo) {
        return String.format(TIPO_IDENTIFICACION_NO_REGISTRADA, tipo);
    }

    public static String tipoIdentificacionRegistrada(String tipo) {
        return String.format(TIPO_IDENTIFICACION_REGISTRADA, tipo);
    }

    public static String identificacionNoRegistrada(String numero) {
        return String.format(IDENTIFICACION_NO_REGISTRADA, numero);
    }

    public static String identificacionYaRegistrada(String numero) {
        return String.format(IDENTIFICACION_YA_REGISTRADA, numero);
    }

    public static String sinClientesPorEdad(Integer edad) {
        return String.format(SIN_CLIENTES_POR_EDAD, edad);
    }
}
