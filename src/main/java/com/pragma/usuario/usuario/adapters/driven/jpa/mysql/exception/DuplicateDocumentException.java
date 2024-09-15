package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.exception;

import com.pragma.usuario.usuario.configuration.Constants;

public class DuplicateDocumentException extends RuntimeException {
    public DuplicateDocumentException() {
        super(Constants.DUPLICATE_DOCUMENT);
    }
}
