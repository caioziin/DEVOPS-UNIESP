package com.uniesp.infrastructure.adapter.in.dto;

import com.uniesp.domain.model.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {

    private final Long          id;
    private final String        name;
    private final BigDecimal    price;
    private final LocalDateTime createdAt;

    private ProductResponse(Long id, String name, BigDecimal price, LocalDateTime createdAt) {
        this.id        = id;
        this.name      = name;
        this.price     = price;
        this.createdAt = createdAt;
    }

    public static ProductResponse fromDomain(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getCreatedAt());
    }

    public Long          getId()        { return id; }
    public String        getName()      { return name; }
    public BigDecimal    getPrice()     { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}