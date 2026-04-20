package com.uniesp.infrastructure.adapter.out;

import com.uniesp.application.port.out.ProductOutputPort;
import com.uniesp.domain.model.Product;
import com.uniesp.infrastructure.adapter.out.entity.ProductEntity;
import com.uniesp.infrastructure.adapter.out.repository.ProductJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryAdapter implements ProductOutputPort {

    private final ProductJpaRepository jpa;

    public ProductRepositoryAdapter(ProductJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAllByOrderByNameAsc().stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id).map(ProductEntity::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByName(name);
    }

    @Override
    public Product save(Product product) {
        return jpa.save(ProductEntity.fromDomain(product)).toDomain();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}