package com.uniesp.application.port.out;

import com.uniesp.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductOutputPort {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    boolean existsByName(String name);

    Product save(Product product);

    void deleteById(Long id);
}