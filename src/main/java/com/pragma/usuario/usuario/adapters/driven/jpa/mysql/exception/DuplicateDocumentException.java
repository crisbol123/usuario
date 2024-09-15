package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.exception;

public class DuplicateDocumentException extends RuntimeException {
    public DuplicateDocumentException() {
        super("Document already exists");
    }
}
