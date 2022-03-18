package com.pragma.customer.infraestructura.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RequestException extends RuntimeException{

    private Integer code;

    public RequestException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
