package com.unimag.consultoriomedico.exception;

public class InvalidAppointmentStatusException extends RuntimeException {
    public InvalidAppointmentStatusException(String message) {
        super(message);
    }
}
