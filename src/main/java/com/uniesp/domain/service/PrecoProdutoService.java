package com.uniesp.domain.service;

import com.uniesp.domain.model.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Migra e corrige a lógica de preço que estava hardcoded
 * no handler Go (/products), sem usar a coluna price do banco.
 *
 * No Go era:
 *   price := 100.0
 *   if name == "Notebook" { price = price * 0.9 }
 *   else if name == "Mouse" { price = price * 0.95 }
 *
 * Agora: usa o preço real do banco e aplica desconto por categoria.
 */
public class PrecoProdutoService {

    // Descontos por categoria de produto
    private static final BigDecimal DESCONTO_NOTEBOOK = new BigDecimal("0.10"); // 10%
    private static final BigDecimal DESCONTO_MOUSE    = new BigDecimal("0.05"); // 5%

    private PrecoProdutoService() {}

    /**
     * Calcula o preço final aplicando desconto conforme o nome do produto.
     * Usa o preço real vindo do banco — não mais hardcoded em 100.0
     */
    public static BigDecimal calcularPrecoFinal(Product product) {
        BigDecimal preco = product.getPrice();
        String nome = product.getName().toLowerCase();

        if (nome.contains("notebook")) {
            return aplicarDesconto(preco, DESCONTO_NOTEBOOK);
        } else if (nome.contains("mouse")) {
            return aplicarDesconto(preco, DESCONTO_MOUSE);
        }

        return preco.setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal aplicarDesconto(BigDecimal preco, BigDecimal percentual) {
        BigDecimal desconto = preco.multiply(percentual);
        return preco.subtract(desconto).setScale(2, RoundingMode.HALF_UP);
    }
}