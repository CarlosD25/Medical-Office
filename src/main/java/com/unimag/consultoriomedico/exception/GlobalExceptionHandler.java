package com.unimag.consultoriomedico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppointmentModificationNotAllowedException.class)
    public ResponseEntity<ApiError> handleAppointmentModificationNotAllowedException(AppointmentModificationNotAllowedException ex) {
        ApiError apiError = ApiError.builder().
                timestamp(LocalDateTime.now()).
                status(HttpStatus.CONFLICT.value()).
                message(ex.getMessage()).
                build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidAppointmentTimeException.class)
    public ResponseEntity<ApiError> handleInvalidAppointmentTimeException(InvalidAppointmentTimeException ex) {
        ApiError apiError = ApiError.builder().
                timestamp(LocalDateTime.now()).
                status(HttpStatus.CONFLICT.value()).
                message(ex.getMessage()).
                build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MedicalRecordCreationException.class)
    public ResponseEntity<ApiError> handleMedicalRecordCreationException(MedicalRecordCreationException ex) {
        ApiError apiError = ApiError.builder().
                timestamp(LocalDateTime.now()).
                status(HttpStatus.CONFLICT.value()).
                message(ex.getMessage()).
                build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = ApiError.builder().
                timestamp(LocalDateTime.now()).
                status(HttpStatus.NOT_FOUND.value()).
                message(ex.getMessage()).
                build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimeConflictException.class)
    public ResponseEntity<ApiError> handleTimeConflictException(TimeConflictException ex) {
        ApiError apiError = ApiError.builder().
                timestamp(LocalDateTime.now()).
                status(HttpStatus.CONFLICT.value()).
                message(ex.getMessage()).
                build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}
