package com.pragma.customer.infraestructura.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class LogicException extends RuntimeException{

    private Integer code;

    public LogicException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
