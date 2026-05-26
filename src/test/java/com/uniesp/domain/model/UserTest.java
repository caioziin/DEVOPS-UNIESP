package com.uniesp.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User - Testes Unitários")
class UserTest {

    // ─────────────────────────────────────────
    // Construtores
    // ─────────────────────────────────────────

    @Test
    @DisplayName("construtor completo: deve inicializar todos os campos")
    void construtorCompleto_inicializaTodosOsCampos() {
        LocalDateTime now = LocalDateTime.now();
        User u = new User(1L, "Pedro Davi", "pedro@email.com", now);

        assertEquals(1L, u.getId());
        assertEquals("Pedro Davi", u.getName());
        assertEquals("pedro@email.com", u.getEmail());
        assertEquals(now, u.getCreatedAt());
    }

    @Test
    @DisplayName("construtor de criação: createdAt não deve ser nulo")
    void construtorCriacao_createdAtNaoNulo() {
        User u = new User("Pedro Davi", "pedro@email.com");
        assertNotNull(u.getCreatedAt());
    }

    @Test
    @DisplayName("construtor de criação: id deve ser nulo (ainda não persistido)")
    void construtorCriacao_idDeveSerNulo() {
        User u = new User("Pedro Davi", "pedro@email.com");
        assertNull(u.getId());
    }

    // ─────────────────────────────────────────
    // updateProfile()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("updateProfile: deve atualizar nome e email corretamente")
    void updateProfile_dadosValidos_atualizaCampos() {
        User u = new User("Pedro", "pedro@email.com");
        u.updateProfile("Pedro Davi", "pedrodavi@email.com");

        assertEquals("Pedro Davi", u.getName());
        assertEquals("pedrodavi@email.com", u.getEmail());
    }

    @Test
    @DisplayName("updateProfile: nome nulo deve lançar IllegalArgumentException")
    void updateProfile_nomeNulo_lancaExcecao() {
        User u = new User("Pedro", "pedro@email.com");
        assertThrows(IllegalArgumentException.class,
                () -> u.updateProfile(null, "pedro@email.com"));
    }

    @Test
    @DisplayName("updateProfile: nome em branco deve lançar IllegalArgumentException")
    void updateProfile_nomeEmBranco_lancaExcecao() {
        User u = new User("Pedro", "pedro@email.com");
        assertThrows(IllegalArgumentException.class,
                () -> u.updateProfile("  ", "pedro@email.com"));
    }

    @Test
    @DisplayName("updateProfile: email nulo deve lançar IllegalArgumentException")
    void updateProfile_emailNulo_lancaExcecao() {
        User u = new User("Pedro", "pedro@email.com");
        assertThrows(IllegalArgumentException.class,
                () -> u.updateProfile("Pedro", null));
    }

    @Test
    @DisplayName("updateProfile: email em branco deve lançar IllegalArgumentException")
    void updateProfile_emailEmBranco_lancaExcecao() {
        User u = new User("Pedro", "pedro@email.com");
        assertThrows(IllegalArgumentException.class,
                () -> u.updateProfile("Pedro", "  "));
    }

    @Test
    @DisplayName("updateProfile: createdAt não deve ser alterado após update")
    void updateProfile_createdAtNaoDeveSerAlterado() {
        User u = new User("Pedro", "pedro@email.com");
        LocalDateTime createdAtOriginal = u.getCreatedAt();
        u.updateProfile("Pedro Davi", "novo@email.com");
        assertEquals(createdAtOriginal, u.getCreatedAt());
    }
}