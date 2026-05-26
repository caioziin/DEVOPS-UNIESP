package com.uniesp.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product - Testes Unitários")
class ProductTest {

    // ─────────────────────────────────────────
    // Construtores
    // ─────────────────────────────────────────

    @Test
    @DisplayName("construtor completo: deve inicializar todos os campos")
    void construtorCompleto_inicializaTodosOsCampos() {
        LocalDateTime now = LocalDateTime.now();
        Product p = new Product(1L, "Notebook", new BigDecimal("1000.00"), now);

        assertEquals(1L, p.getId());
        assertEquals("Notebook", p.getName());
        assertEquals(new BigDecimal("1000.00"), p.getPrice());
        assertEquals(now, p.getCreatedAt());
    }

    @Test
    @DisplayName("construtor de criação: createdAt não deve ser nulo")
    void construtorCriacao_createdAtNaoNulo() {
        Product p = new Product("Mouse", new BigDecimal("150.00"));
        assertNotNull(p.getCreatedAt());
    }

    // ─────────────────────────────────────────
    // updateDetails()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("updateDetails: deve atualizar nome e preço corretamente")
    void updateDetails_dadosValidos_atualizaCampos() {
        Product p = new Product("Teclado", new BigDecimal("200.00"));
        p.updateDetails("Teclado Mecânico", new BigDecimal("350.00"));

        assertEquals("Teclado Mecânico", p.getName());
        assertEquals(new BigDecimal("350.00"), p.getPrice());
    }

    @Test
    @DisplayName("updateDetails: nome nulo deve lançar IllegalArgumentException")
    void updateDetails_nomeNulo_lancaExcecao() {
        Product p = new Product("Teclado", new BigDecimal("200.00"));
        assertThrows(IllegalArgumentException.class,
                () -> p.updateDetails(null, new BigDecimal("350.00")));
    }

    @Test
    @DisplayName("updateDetails: nome em branco deve lançar IllegalArgumentException")
    void updateDetails_nomeEmBranco_lancaExcecao() {
        Product p = new Product("Teclado", new BigDecimal("200.00"));
        assertThrows(IllegalArgumentException.class,
                () -> p.updateDetails("  ", new BigDecimal("350.00")));
    }

    @Test
    @DisplayName("updateDetails: preço zero deve lançar IllegalArgumentException")
    void updateDetails_precoZero_lancaExcecao() {
        Product p = new Product("Teclado", new BigDecimal("200.00"));
        assertThrows(IllegalArgumentException.class,
                () -> p.updateDetails("Teclado", BigDecimal.ZERO));
    }

    @Test
    @DisplayName("updateDetails: preço negativo deve lançar IllegalArgumentException")
    void updateDetails_precoNegativo_lancaExcecao() {
        Product p = new Product("Teclado", new BigDecimal("200.00"));
        assertThrows(IllegalArgumentException.class,
                () -> p.updateDetails("Teclado", new BigDecimal("-1.00")));
    }

    @Test
    @DisplayName("updateDetails: preço nulo deve lançar IllegalArgumentException")
    void updateDetails_precoNulo_lancaExcecao() {
        Product p = new Product("Teclado", new BigDecimal("200.00"));
        assertThrows(IllegalArgumentException.class,
                () -> p.updateDetails("Teclado", null));
    }
}