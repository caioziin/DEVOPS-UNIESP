package com.uniesp.domain.exception;

// Exceção de domínio — não depende de Spring
public class ResourceNotFoundException extends RuntimeException {

    private final String resource;
    private final Long   id;

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " não encontrado com id: " + id);
        this.resource = resource;
        this.id       = id;
    }

    public String getResource() { return resource; }
    public Long   getId()       { return id; }
}