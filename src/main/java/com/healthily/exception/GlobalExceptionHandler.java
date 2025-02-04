package com.healthily.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {

        return ResponseEntity.status(BAD_REQUEST).body(getBodyResponse(BAD_REQUEST, ex));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAuthUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(getBodyResponse(NOT_FOUND, ex));
    }

    private static Map<String, Object> getBodyResponse(HttpStatus status, Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("error", status.value());
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(getBodyResponse(INTERNAL_SERVER_ERROR, ex));
    }
}
