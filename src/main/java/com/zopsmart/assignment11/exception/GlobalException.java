package com.zopsmart.assignment11.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalException.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceNotFound(ResourceNotFoundException EntityNotFound) {
        LOGGER.error(EntityNotFound.getMessage());
        return new ResponseEntity<>(EntityNotFound.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequest(BadRequestException badRequest) {
        LOGGER.error(badRequest.getMessage());
        return new ResponseEntity<>(badRequest.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> Exception(Exception serverError) {
        LOGGER.error(serverError.getMessage());
        return new ResponseEntity<>(serverError.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
