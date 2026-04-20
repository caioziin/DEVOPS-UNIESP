package com.uniesp.domain.model;

import java.time.LocalDateTime;

// Objeto de domínio puro — zero dependência de Spring ou JPA
public class User {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    // Construtor completo (usado pelos adapters ao montar o domínio)
    public User(Long id, String name, String email, LocalDateTime createdAt) {
        this.id        = id;
        this.name      = name;
        this.email     = email;
        this.createdAt = createdAt;
    }

    // Construtor de criação (sem id — banco vai gerar)
    public User(String name, String email) {
        this.name  = name;
        this.email = email;
    }

    // Getters
    public Long getId()                { return id; }
    public String getName()            { return name; }
    public String getEmail()           { return email; }
    public LocalDateTime getCreatedAt(){ return createdAt; }

    // Setters — apenas campos mutáveis pelo negócio
    public void setName(String name)   { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}