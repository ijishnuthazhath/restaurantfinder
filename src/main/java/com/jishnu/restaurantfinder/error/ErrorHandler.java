package com.jishnu.restaurantfinder.error;

import com.jishnu.restaurantfinder.exception.CoordinatesInvalidException;
import com.jishnu.restaurantfinder.exception.CoordinatesOutOfBoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleException(final Exception e) {
        log.error("An error occurred", e);
        return ResponseEntity.status(500).body("Something went wrong");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(final CoordinatesOutOfBoundException e) {
        log.error("Coordinated give are invalid", e);
        return ResponseEntity.badRequest().body("Coordinated give are invalid");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(final CoordinatesInvalidException e) {
        log.error("Coordinated give are invalid", e);
        return ResponseEntity.badRequest().body("Coordinated give are invalid");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().build();
    }
}
