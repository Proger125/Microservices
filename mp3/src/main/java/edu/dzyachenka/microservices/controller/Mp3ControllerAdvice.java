package edu.dzyachenka.microservices.controller;

import edu.dzyachenka.microservices.exception.FormatNotSupportedException;
import edu.dzyachenka.microservices.exception.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class Mp3ControllerAdvice {

    @ExceptionHandler(value = FormatNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleFormatNotSupportedException(final FormatNotSupportedException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ExceptionDto> handleNoSuchElementException(final NoSuchElementException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
