package com.uniesp.infrastructure.adapter.out.mapper;

import com.uniesp.domain.model.Product;
import com.uniesp.infrastructure.adapter.out.entity.ProductEntity;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductEntity toEntity(Product p) {
        ProductEntity e = new ProductEntity();
        e.setId(p.getId());
        e.setName(p.getName());
        e.setPrice(p.getPrice());
        e.setCreatedAt(p.getCreatedAt());
        return e;
    }

    public static Product toDomain(ProductEntity e) {
        return new Product(e.getId(), e.getName(), e.getPrice(), e.getCreatedAt());
    }
}