package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.exception;

import com.pragma.usuario.usuario.configuration.Constants;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super(Constants.DUPLICATE_EMAIL);
    }
}
