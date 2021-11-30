package com.israelgda.cadastroapi.services.exceptions;

public class PostalCodeInvalidFormatException extends RuntimeException {
    public PostalCodeInvalidFormatException(String message) {
        super(message);
    }
}
