package cn.batenglish.gallery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.NoSuchAlgorithmException;

@ControllerAdvice
public class ErrorController {
//
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<String> handleNoSuchAlgorithmException(NoSuchAlgorithmException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
//        ErrorResponse error = new ErrorResponse();
//        error.setMessage(ex.getMessage());
//        error.setStatus(HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

}

