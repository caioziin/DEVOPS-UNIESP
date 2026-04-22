package com.uniesp.infrastructure.adapter.out.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// SRP: entity só conhece JPA — conversão saiu daqui para o UserMapper
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
    }

    public Long getId()                 { return id; }
    public String getName()             { return name; }
    public String getEmail()            { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id)                       { this.id = id; }
    public void setName(String name)                 { this.name = name; }
    public void setEmail(String email)               { this.email = email; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }
}