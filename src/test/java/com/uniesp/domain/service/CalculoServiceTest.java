package com.uniesp.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CalculoService - Testes Unitários")
class CalculoServiceTest {

    // ─────────────────────────────────────────
    // soma()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("soma: deve retornar a soma correta de dois positivos")
    void soma_doisPositivos_retornaSomaCorreta() {
        assertEquals(5, CalculoService.soma(2, 3));
    }

    @Test
    @DisplayName("soma: bug corrigido — não deve somar +1 extra (era a+b+1 no Go)")
    void soma_bugCorrigido_naoAdicionaUmExtra() {
        assertNotEquals(6, CalculoService.soma(2, 3)); // bug antigo retornava 6
        assertEquals(5,  CalculoService.soma(2, 3)); // correto retorna 5
    }

    @Test
    @DisplayName("soma: deve funcionar com zero")
    void soma_comZero_retornaOProprioNumero() {
        assertEquals(7, CalculoService.soma(7, 0));
        assertEquals(7, CalculoService.soma(0, 7));
    }

    @Test
    @DisplayName("soma: deve funcionar com negativos")
    void soma_comNegativos_retornaResultadoCorreto() {
        assertEquals(-1, CalculoService.soma(-3, 2));
        assertEquals(-5, CalculoService.soma(-2, -3));
    }

    // ─────────────────────────────────────────
    // calc()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("calc: bug corrigido — 10 deve retornar 'Grande' (era x>10 no Go)")
    void calc_dez_retornaGrande() {
        assertEquals("Grande", CalculoService.calc(10));
    }

    @Test
    @DisplayName("calc: valor acima de 10 deve retornar 'Grande'")
    void calc_acimaDeZes_retornaGrande() {
        assertEquals("Grande", CalculoService.calc(15));
        assertEquals("Grande", CalculoService.calc(100));
    }

    @Test
    @DisplayName("calc: valor entre 6 e 9 deve retornar 'Medio'")
    void calc_entre6e9_retornaMedio() {
        assertEquals("Medio", CalculoService.calc(6));
        assertEquals("Medio", CalculoService.calc(9));
        assertEquals("Medio", CalculoService.calc(7));
    }

    @Test
    @DisplayName("calc: valor 5 deve retornar 'Pequeno'")
    void calc_cinco_retornaPequeno() {
        assertEquals("Pequeno", CalculoService.calc(5));
    }

    @Test
    @DisplayName("calc: valor abaixo de 5 deve retornar 'Pequeno'")
    void calc_abaixoDeCinco_retornaPequeno() {
        assertEquals("Pequeno", CalculoService.calc(0));
        assertEquals("Pequeno", CalculoService.calc(-10));
    }
}
