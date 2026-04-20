package com.uniesp.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private final LocalDateTime createdAt;

    public Product(Long id, String name, BigDecimal price, LocalDateTime createdAt) {
        this.id        = id;
        this.name      = name;
        this.price     = price;
        this.createdAt = createdAt;
    }

    public Product(String name, BigDecimal price) {
        this.name      = name;
        this.price     = price;
        this.createdAt = LocalDateTime.now();
    }

    // SRP: regra de atualização encapsulada no domínio
    public void updateDetails(String name, BigDecimal price) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nome do produto inválido");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        this.name  = name;
        this.price = price;
    }

    public Long getId()                 { return id; }
    public String getName()             { return name; }
    public BigDecimal getPrice()        { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}