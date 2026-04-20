package com.uniesp.domain.exception;

// Violações de regra de negócio (ex: e-mail duplicado)
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}