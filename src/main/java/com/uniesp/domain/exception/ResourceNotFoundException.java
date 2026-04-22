package com.uniesp.domain.exception;

import lombok.Getter;

// Exceção de domínio — não depende de Spring
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String resource;
    private final Long   id;

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " não encontrado com id: " + id);
        this.resource = resource;
        this.id       = id;
    }

}