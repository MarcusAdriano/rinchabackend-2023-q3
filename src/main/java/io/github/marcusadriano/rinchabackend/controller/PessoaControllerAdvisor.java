package io.github.marcusadriano.rinchabackend.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PessoaControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleDateTimeParseException(final MethodArgumentNotValidException ex) {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleDateTimeParseException(final InvalidFormatException ex) {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleDuplicateKeyException(final DuplicateKeyException ex) {
        return ResponseEntity.unprocessableEntity().build();
    }
}
