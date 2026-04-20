package com.uniesp.infrastructure.adapter.out.entity;

import com.uniesp.domain.model.Product;
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
        this.createdAt = LocalDateTime.now();
    }

    public static ProductEntity fromDomain(Product p) {
        ProductEntity e = new ProductEntity();
        e.id        = p.getId();
        e.name      = p.getName();
        e.price     = p.getPrice();
        e.createdAt = p.getCreatedAt();
        return e;
    }

    public Product toDomain() {
        return new Product(id, name, price, createdAt);
    }

    public Long getId()                 { return id; }
    public String getName()             { return name; }
    public BigDecimal getPrice()        { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}