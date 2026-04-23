package com.uniesp.infrastructure.adapter.out.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
    }

    public Long getId()                 { return id; }
    public String getName()             { return name; }
    public BigDecimal getPrice()        { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id)                       { this.id = id; }
    public void setName(String name)                 { this.name = name; }
    public void setPrice(BigDecimal price)            { this.price = price; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }
}