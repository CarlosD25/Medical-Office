package com.unimag.consultoriomedico.exception;

public class AppointmentModificationNotAllowedException extends RuntimeException {
    public AppointmentModificationNotAllowedException(String message) { super(message); }
}
