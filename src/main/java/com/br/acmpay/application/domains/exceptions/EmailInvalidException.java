package com.br.acmpay.application.domains.exceptions;

public class EmailInvalidException extends Exception {
    public EmailInvalidException(String message) {
        super(message);
    }
}
