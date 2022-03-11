package com.pragma.customer.infraestructura.exceptions;

import com.pragma.customer.dominio.modelo.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<?> LogiceExceptionHandler(RequestException ex) {
        ErrorDto errorDto = ErrorDto.builder().codigo(ex.getCode()).mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, ex.getStatus());
    }
    @ExceptionHandler(value = LogicException.class)
    public ResponseEntity<?> requestExceptionHandler(LogicException ex) {
        ErrorDto errorDto = ErrorDto.builder().codigo(ex.getCode()).mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, ex.getStatus());
    }
}
