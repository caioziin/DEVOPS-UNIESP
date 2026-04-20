package com.uniesp.infrastructure.adapter.in.dto;

import com.uniesp.domain.model.User;
import java.time.LocalDateTime;

public class UserResponse {

    private final Long          id;
    private final String        name;
    private final String        email;
    private final LocalDateTime createdAt;

    private UserResponse(Long id, String name, String email, LocalDateTime createdAt) {
        this.id        = id;
        this.name      = name;
        this.email     = email;
        this.createdAt = createdAt;
    }

    // Factory method — converte domínio em resposta REST
    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(), user.getName(),
                user.getEmail(), user.getCreatedAt());
    }

    public Long          getId()        { return id; }
    public String        getName()      { return name; }
    public String        getEmail()     { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}