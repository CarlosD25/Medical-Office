package com.unimag.consultoriomedico.exception;

public class InvalidAppointmentDurationException extends RuntimeException {
    public InvalidAppointmentDurationException(String message) {
        super(message);
    }
}
