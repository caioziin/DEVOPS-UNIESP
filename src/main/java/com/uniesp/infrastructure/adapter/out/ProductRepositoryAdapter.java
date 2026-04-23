package com.uniesp.infrastructure.adapter.out;

import com.uniesp.application.port.out.ProductOutputPort;
import com.uniesp.domain.model.Product;
import com.uniesp.infrastructure.adapter.out.mapper.ProductMapper;
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
        return jpa.findAllByOrderByNameAsc().stream().map(ProductMapper::toDomain).toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByName(name);
    }

    @Override
    public Product save(Product product) {
        return ProductMapper.toDomain(jpa.save(ProductMapper.toEntity(product)));
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}