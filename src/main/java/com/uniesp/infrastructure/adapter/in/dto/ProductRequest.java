package com.uniesp.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Digits(integer = 8, fraction = 2, message = "Formato de preço inválido")
    private BigDecimal price;

    public String     getName()  { return name; }
    public BigDecimal getPrice() { return price; }
    public void setName(String name)      { this.name = name; }
    public void setPrice(BigDecimal price){ this.price = price; }
}