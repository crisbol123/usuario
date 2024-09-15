package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super("Email already exists");
    }
}
