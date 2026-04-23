package com.uniesp.application.port.in;

import com.uniesp.domain.model.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductInputPort {

    List<Product> findAll();

    Product findById(Long id);

    Product create(String name, BigDecimal price);

    Product update(Long id, String name, BigDecimal price);

    void delete(Long id);
}