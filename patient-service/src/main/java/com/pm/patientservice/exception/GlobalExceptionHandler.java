package com.pm.patientservice.exception;

import jakarta.persistence.ElementCollection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String,String>> handlEmailAlreadyException(EmailAlreadyExistException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email already exist");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(PatientNotFondException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFondException(PatientNotFondException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Patient not fond");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errors);
    }


}
