package com.israelgda.cadastroapi.services.exceptions;

public class PostalCodeNotFound extends RuntimeException {
    public PostalCodeNotFound(String message) {
        super(message);
    }
}
