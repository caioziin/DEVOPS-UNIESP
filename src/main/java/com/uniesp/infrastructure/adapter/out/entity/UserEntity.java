package com.uniesp.infrastructure.adapter.out.entity;

import com.uniesp.domain.model.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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
        this.createdAt = LocalDateTime.now();
    }

    // ── Conversão domínio → entity ──────────────────────────────────────
    public static UserEntity fromDomain(User user) {
        UserEntity e = new UserEntity();
        e.id        = user.getId();
        e.name      = user.getName();
        e.email     = user.getEmail();
        e.createdAt = user.getCreatedAt();
        return e;
    }

    // ── Conversão entity → domínio ──────────────────────────────────────
    public User toDomain() {
        return new User(id, name, email, createdAt);
    }

    // Getters
    public Long getId()                 { return id; }
    public String getName()             { return name; }
    public String getEmail()            { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}