package com.uniesp.domain.model;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String name;
    private String email;
    private final LocalDateTime createdAt;

    // Construtor completo (reconstrói do banco)
    public User(Long id, String name, String email, LocalDateTime createdAt) {
        this.id        = id;
        this.name      = name;
        this.email     = email;
        this.createdAt = createdAt;
    }

    // Construtor de criação — domínio define o momento de criação
    public User(String name, String email) {
        this.name      = name;
        this.email     = email;
        this.createdAt = LocalDateTime.now();
    }

    // SRP: regra de negócio de atualização dentro do próprio domínio
    public void updateProfile(String name, String email) {
        if (name == null || name.isBlank())  throw new IllegalArgumentException("Nome inválido");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("E-mail inválido");
        this.name  = name;
        this.email = email;
    }

    public Long getId()                { return id; }
    public String getName()            { return name; }
    public String getEmail()           { return email; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
}