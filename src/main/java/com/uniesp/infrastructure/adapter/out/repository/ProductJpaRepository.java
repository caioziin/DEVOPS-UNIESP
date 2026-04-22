package com.uniesp.infrastructure.adapter.out.repository;

import com.uniesp.infrastructure.adapter.out.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByOrderByNameAsc();
    boolean existsByName(String name);
}