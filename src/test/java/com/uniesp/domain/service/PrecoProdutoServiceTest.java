package com.uniesp.domain.service;

import com.uniesp.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PrecoProdutoService - Testes Unitários")
class PrecoProdutoServiceTest {

    // ─────────────────────────────────────────
    // calcularPrecoFinal() — Notebook (10% off)
    // ─────────────────────────────────────────

    @Test
    @DisplayName("notebook: deve aplicar 10% de desconto")
    void calcularPrecoFinal_notebook_aplicaDesconto10Porcento() {
        Product product = new Product("Notebook Gamer", new BigDecimal("1000.00"));
        BigDecimal resultado = PrecoProdutoService.calcularPrecoFinal(product);
        assertEquals(new BigDecimal("900.00"), resultado);
    }

    @Test
    @DisplayName("notebook: nome em maiúsculo também deve receber desconto")
    void calcularPrecoFinal_notebookMaiusculo_aplicaDesconto() {
        Product product = new Product("NOTEBOOK Pro", new BigDecimal("500.00"));
        BigDecimal resultado = PrecoProdutoService.calcularPrecoFinal(product);
        assertEquals(new BigDecimal("450.00"), resultado);
    }

    // ─────────────────────────────────────────
    // calcularPrecoFinal() — Mouse (5% off)
    // ─────────────────────────────────────────

    @Test
    @DisplayName("mouse: deve aplicar 5% de desconto")
    void calcularPrecoFinal_mouse_aplicaDesconto5Porcento() {
        Product product = new Product("Mouse Wireless", new BigDecimal("200.00"));
        BigDecimal resultado = PrecoProdutoService.calcularPrecoFinal(product);
        assertEquals(new BigDecimal("190.00"), resultado);
    }

    @Test
    @DisplayName("mouse: nome em maiúsculo também deve receber desconto")
    void calcularPrecoFinal_mouseMaiusculo_aplicaDesconto() {
        Product product = new Product("MOUSE Gamer RGB", new BigDecimal("100.00"));
        BigDecimal resultado = PrecoProdutoService.calcularPrecoFinal(product);
        assertEquals(new BigDecimal("95.00"), resultado);
    }

    // ─────────────────────────────────────────
    // calcularPrecoFinal() — Sem desconto
    // ─────────────────────────────────────────

    @Test
    @DisplayName("produto sem categoria: deve retornar preço original sem desconto")
    void calcularPrecoFinal_produtoSemCategoria_retornaPrecoOriginal() {
        Product product = new Product("Teclado Mecânico", new BigDecimal("350.00"));
        BigDecimal resultado = PrecoProdutoService.calcularPrecoFinal(product);
        assertEquals(new BigDecimal("350.00"), resultado);
    }

    @Test
    @DisplayName("resultado deve sempre ter 2 casas decimais")
    void calcularPrecoFinal_sempreRetornaDuasCasasDecimais() {
        Product product = new Product("Monitor", new BigDecimal("999.999"));
        BigDecimal resultado = PrecoProdutoService.calcularPrecoFinal(product);
        assertEquals(2, resultado.scale());
    }

    @Test
    @DisplayName("bug corrigido: deve usar o preço real do banco, não hardcoded 100.0")
    void calcularPrecoFinal_usaPrecoRealNaoHardcoded() {
        Product barato  = new Product("Notebook Básico", new BigDecimal("200.00"));
        Product caro    = new Product("Notebook Pro",    new BigDecimal("5000.00"));
        assertNotEquals(PrecoProdutoService.calcularPrecoFinal(barato),
                PrecoProdutoService.calcularPrecoFinal(caro));
    }
}
