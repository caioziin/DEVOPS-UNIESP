package com.uniesp.domain.service;

import com.uniesp.domain.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserValidationService - Testes Unitários")
class UserValidationServiceTest {

    // ─────────────────────────────────────────
    // validarNome()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("nome válido: não deve lançar exceção")
    void validarNome_nomeValido_naoLancaExcecao() {
        assertDoesNotThrow(() -> UserValidationService.validarNome("Pedro Davi"));
    }

    @Test
    @DisplayName("nome nulo: deve lançar BusinessException")
    void validarNome_nomeNulo_lancaExcecao() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> UserValidationService.validarNome(null));
        assertEquals("O nome é obrigatório", ex.getMessage());
    }

    @Test
    @DisplayName("nome em branco: deve lançar BusinessException")
    void validarNome_nomeEmBranco_lancaExcecao() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> UserValidationService.validarNome("   "));
        assertEquals("O nome é obrigatório", ex.getMessage());
    }

    @Test
    @DisplayName("nome com 1 caractere: deve lançar BusinessException (mínimo 2)")
    void validarNome_nomeMuitoCurto_lancaExcecao() {
        assertThrows(BusinessException.class,
                () -> UserValidationService.validarNome("A"));
    }

    @Test
    @DisplayName("nome com 2 caracteres: deve ser válido (limite mínimo)")
    void validarNome_nomeLimiteMinimo_naoLancaExcecao() {
        assertDoesNotThrow(() -> UserValidationService.validarNome("Jo"));
    }

    @Test
    @DisplayName("nome com 100 caracteres: deve ser válido (limite máximo)")
    void validarNome_nomeLimiteMaximo_naoLancaExcecao() {
        String nome100 = "A".repeat(100);
        assertDoesNotThrow(() -> UserValidationService.validarNome(nome100));
    }

    @Test
    @DisplayName("nome com 101 caracteres: deve lançar BusinessException")
    void validarNome_nomeAcimaDoMaximo_lancaExcecao() {
        String nome101 = "A".repeat(101);
        assertThrows(BusinessException.class,
                () -> UserValidationService.validarNome(nome101));
    }

    // ─────────────────────────────────────────
    // validarEmail()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("email válido: não deve lançar exceção")
    void validarEmail_emailValido_naoLancaExcecao() {
        assertDoesNotThrow(() -> UserValidationService.validarEmail("pedro@email.com"));
    }

    @Test
    @DisplayName("email nulo: deve lançar BusinessException")
    void validarEmail_emailNulo_lancaExcecao() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> UserValidationService.validarEmail(null));
        assertEquals("O e-mail é obrigatório", ex.getMessage());
    }

    @Test
    @DisplayName("email em branco: deve lançar BusinessException")
    void validarEmail_emailEmBranco_lancaExcecao() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> UserValidationService.validarEmail(""));
        assertEquals("O e-mail é obrigatório", ex.getMessage());
    }

    @Test
    @DisplayName("email sem @: deve lançar BusinessException")
    void validarEmail_emailSemArroba_lancaExcecao() {
        assertThrows(BusinessException.class,
                () -> UserValidationService.validarEmail("emailsemarroba.com"));
    }

    @Test
    @DisplayName("email sem domínio: deve lançar BusinessException")
    void validarEmail_emailSemDominio_lancaExcecao() {
        assertThrows(BusinessException.class,
                () -> UserValidationService.validarEmail("email@"));
    }

    @Test
    @DisplayName("email com mais de 150 caracteres: deve lançar BusinessException")
    void validarEmail_emailMuitoLongo_lancaExcecao() {
        String emailLongo = "a".repeat(145) + "@a.com"; // > 150 chars
        assertThrows(BusinessException.class,
                () -> UserValidationService.validarEmail(emailLongo));
    }

    @Test
    @DisplayName("email com subdomínio: deve ser válido")
    void validarEmail_emailComSubdominio_naoLancaExcecao() {
        assertDoesNotThrow(() -> UserValidationService.validarEmail("pedro@mail.uniesp.edu.br"));
    }
}