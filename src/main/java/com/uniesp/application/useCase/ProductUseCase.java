package com.uniesp.application.useCase;

import com.uniesp.application.port.in.ProductInputPort;
import com.uniesp.application.port.out.ProductOutputPort;
import com.uniesp.domain.exception.BusinessException;
import com.uniesp.domain.exception.ResourceNotFoundException;
import com.uniesp.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductUseCase implements ProductInputPort {

    private final ProductOutputPort productOutputPort;

    public ProductUseCase(ProductOutputPort productOutputPort) {
        this.productOutputPort = productOutputPort;
    }

    @Override
    public List<Product> findAll() {
        return productOutputPort.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productOutputPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));
    }

    @Override
    public Product create(String name, BigDecimal price) {
        if (productOutputPort.existsByName(name)) {
            throw new BusinessException("Já existe um produto com o nome: " + name);
        }
        return productOutputPort.save(new Product(name, price));
    }

    @Override
    public Product update(Long id, String name, BigDecimal price) {
        Product product = findById(id);
        // SRP: regra de mutação delegada ao domínio
        product.updateDetails(name, price);
        return productOutputPort.save(product);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        productOutputPort.deleteById(id);
    }
}