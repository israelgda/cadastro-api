package com.israelgda.cadastroapi.services.exceptions;

public class DataFormatViolationException extends RuntimeException {
    public DataFormatViolationException(String message) {
        super(message);
    }
}
