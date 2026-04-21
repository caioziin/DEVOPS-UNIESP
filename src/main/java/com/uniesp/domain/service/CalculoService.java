package com.uniesp.domain.service;

// Migração direta das funções Go — bugs corrigidos
// SRP: única responsabilidade — cálculos de negócio
public class CalculoService {

    private CalculoService() {}

    /**
     * Corrige o bug do Go: era "return a + b + 1"
     * Agora retorna corretamente a + b
     */
    public static int soma(int a, int b) {
        return a + b;
    }

    /**
     * Corrige o bug do Go: era "x > 10" (excluía o 10)
     * Agora usa "x >= 10" — Calc(10) retorna "Grande" corretamente
     *
     * Regra de negócio:
     *   >= 10        → Grande
     *   > 5 e < 10  → Medio
     *   <= 5         → Pequeno
     */
    public static String calc(int x) {
        if (x >= 10) {
            return "Grande";
        } else if (x > 5) {
            return "Medio";
        }
        return "Pequeno";
    }
}