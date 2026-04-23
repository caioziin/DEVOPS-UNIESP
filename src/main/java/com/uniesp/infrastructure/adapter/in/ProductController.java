package com.uniesp.infrastructure.adapter.in;

import com.uniesp.application.port.in.ProductInputPort;
import com.uniesp.infrastructure.adapter.in.dto.ProductRequest;
import com.uniesp.infrastructure.adapter.in.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductInputPort productInputPort;

    public ProductController(ProductInputPort productInputPort) {
        this.productInputPort = productInputPort;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productInputPort.findAll().stream()
                .map(ProductResponse::fromDomain).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ProductResponse.fromDomain(productInputPort.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
        ProductResponse created = ProductResponse.fromDomain(
                productInputPort.create(req.getName(), req.getPrice()));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(ProductResponse.fromDomain(
                productInputPort.update(id, req.getName(), req.getPrice())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }
}