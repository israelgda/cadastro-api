package com.israelgda.cadastroapi.services.exceptions;

public class CpfAlredyRegistered extends RuntimeException {
    public CpfAlredyRegistered(String message) {
        super(message);
    }
}
