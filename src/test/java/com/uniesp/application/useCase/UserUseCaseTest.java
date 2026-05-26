package com.uniesp.application.useCase;

import com.uniesp.application.port.out.UserOutputPort;
import com.uniesp.domain.exception.BusinessException;
import com.uniesp.domain.exception.ResourceNotFoundException;
import com.uniesp.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserUseCase - Testes com Mockito")
class UserUseCaseTest {

    @Mock
    private UserOutputPort userOutputPort;

    @InjectMocks
    private UserUseCase userUseCase;

    // ─────────────────────────────────────────
    // findAll()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("findAll: deve retornar lista de usuários")
    void findAll_retornaListaDeUsuarios() {
        List<User> usuarios = List.of(
                new User("Pedro Davi", "pedro@email.com"),
                new User("João Silva", "joao@email.com")
        );
        when(userOutputPort.findAll()).thenReturn(usuarios);

        List<User> resultado = userUseCase.findAll();

        assertEquals(2, resultado.size());
        verify(userOutputPort, times(1)).findAll();
    }

    // ─────────────────────────────────────────
    // findById()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("findById: deve retornar usuário quando encontrado")
    void findById_usuarioExistente_retornaUsuario() {
        User user = new User(1L, "Pedro Davi", "pedro@email.com", null);
        when(userOutputPort.findById(1L)).thenReturn(Optional.of(user));

        User resultado = userUseCase.findById(1L);

        assertEquals("Pedro Davi", resultado.getName());
        verify(userOutputPort, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById: deve lançar ResourceNotFoundException quando não encontrado")
    void findById_usuarioNaoExistente_lancaExcecao() {
        when(userOutputPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userUseCase.findById(99L));
    }

    // ─────────────────────────────────────────
    // create()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("create: deve criar usuário com dados válidos")
    void create_dadosValidos_criaUsuario() {
        User user = new User("Pedro Davi", "pedro@email.com");
        when(userOutputPort.existsByEmail("pedro@email.com")).thenReturn(false);
        when(userOutputPort.save(any(User.class))).thenReturn(user);

        User resultado = userUseCase.create("Pedro Davi", "pedro@email.com");

        assertEquals("Pedro Davi", resultado.getName());
        verify(userOutputPort, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("create: deve lançar BusinessException quando email já existe")
    void create_emailDuplicado_lancaExcecao() {
        when(userOutputPort.existsByEmail("pedro@email.com")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userUseCase.create("Pedro Davi", "pedro@email.com"));

        assertTrue(ex.getMessage().contains("pedro@email.com"));
        verify(userOutputPort, never()).save(any());
    }

    @Test
    @DisplayName("create: deve lançar BusinessException para nome inválido")
    void create_nomeInvalido_lancaExcecao() {
        assertThrows(BusinessException.class,
                () -> userUseCase.create("", "pedro@email.com"));
        verify(userOutputPort, never()).save(any());
    }

    @Test
    @DisplayName("create: deve lançar BusinessException para email inválido")
    void create_emailInvalido_lancaExcecao() {
        assertThrows(BusinessException.class,
                () -> userUseCase.create("Pedro Davi", "emailinvalido"));
        verify(userOutputPort, never()).save(any());
    }

    // ─────────────────────────────────────────
    // update()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("update: deve atualizar usuário com dados válidos")
    void update_dadosValidos_atualizaUsuario() {
        User user = new User(1L, "Pedro", "pedro@email.com", null);
        when(userOutputPort.findById(1L)).thenReturn(Optional.of(user));
        when(userOutputPort.existsByEmail("novo@email.com")).thenReturn(false);
        when(userOutputPort.save(any(User.class))).thenReturn(user);

        User resultado = userUseCase.update(1L, "Pedro Davi", "novo@email.com");

        assertEquals("Pedro Davi", resultado.getName());
        verify(userOutputPort, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("update: deve lançar BusinessException quando email já está em uso por outro")
    void update_emailEmUso_lancaExcecao() {
        User user = new User(1L, "Pedro", "pedro@email.com", null);
        when(userOutputPort.findById(1L)).thenReturn(Optional.of(user));
        when(userOutputPort.existsByEmail("outro@email.com")).thenReturn(true);

        assertThrows(BusinessException.class,
                () -> userUseCase.update(1L, "Pedro Davi", "outro@email.com"));
        verify(userOutputPort, never()).save(any());
    }

    @Test
    @DisplayName("update: deve permitir manter o mesmo email")
    void update_mesmoEmail_atualizaSemErro() {
        User user = new User(1L, "Pedro", "pedro@email.com", null);
        when(userOutputPort.findById(1L)).thenReturn(Optional.of(user));
        when(userOutputPort.existsByEmail("pedro@email.com")).thenReturn(true);
        when(userOutputPort.save(any(User.class))).thenReturn(user);

        assertDoesNotThrow(() -> userUseCase.update(1L, "Pedro Davi", "pedro@email.com"));
    }

    // ─────────────────────────────────────────
    // delete()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("delete: deve deletar usuário existente")
    void delete_usuarioExistente_deletaComSucesso() {
        User user = new User(1L, "Pedro", "pedro@email.com", null);
        when(userOutputPort.findById(1L)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userUseCase.delete(1L));
        verify(userOutputPort, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete: deve lançar ResourceNotFoundException para usuário inexistente")
    void delete_usuarioInexistente_lancaExcecao() {
        when(userOutputPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userUseCase.delete(99L));
        verify(userOutputPort, never()).deleteById(any());
    }
}