package com.zopsmart.assignment11.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceNotFound(ResourceNotFoundException EntityNotFound) {
        return new ResponseEntity<>(EntityNotFound.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequest(BadRequestException badRequest) {
        return new ResponseEntity<>(badRequest.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> Exception(Exception serverError) {
        return new ResponseEntity<>(serverError.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
