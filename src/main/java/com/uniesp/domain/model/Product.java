package com.uniesp.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public Product(Long id, String name, BigDecimal price, LocalDateTime createdAt) {
        this.id        = id;
        this.name      = name;
        this.price     = price;
        this.createdAt = createdAt;
    }

    public Product(String name, BigDecimal price) {
        this.name  = name;
        this.price = price;
    }

    public Long getId()                 { return id; }
    public String getName()             { return name; }
    public BigDecimal getPrice()        { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setName(String name)    { this.name = name; }
    public void setPrice(BigDecimal p)  { this.price = p; }
}