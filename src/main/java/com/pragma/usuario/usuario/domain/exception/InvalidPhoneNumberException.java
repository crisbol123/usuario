package com.pragma.usuario.usuario.domain.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException() {
        super("Invalid phone number");
    }
}
