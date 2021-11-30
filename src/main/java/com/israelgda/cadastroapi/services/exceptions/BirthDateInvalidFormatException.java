package com.israelgda.cadastroapi.services.exceptions;

public class BirthDateInvalidFormatException extends RuntimeException {
    public BirthDateInvalidFormatException(String message) {
        super(message);
    }
}
