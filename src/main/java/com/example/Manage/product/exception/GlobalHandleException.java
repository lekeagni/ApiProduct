package com.example.Manage.product.exception;

import com.example.Manage.product.model.ErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class GlobalHandleException {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorException> ProductNotFoundException (ProductNotFoundException exception){
        ErrorException errorException = ErrorException.builder()
                .localDateTime(LocalDateTime.now())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorException);
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<ErrorException> ProductAlreadyExistException (ProductAlreadyExistException exception){
        ErrorException errorException = ErrorException.builder()
                .localDateTime(LocalDateTime.now())
                .httpStatus(HttpStatus.CONTINUE.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorException);
    }
}
