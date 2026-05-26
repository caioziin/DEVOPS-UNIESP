package infrastructure.adapter.out;

import com.uniesp.application.port.out.UserOutputPort;
import com.uniesp.application.useCase.UserUseCase;
import com.uniesp.domain.model.User;
import com.uniesp.infrastructure.adapter.out.UserRepositoryAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({UserRepositoryAdapter.class, UserUseCase.class})
@DisplayName("UserRepositoryAdapter - Testes de Integração com H2")
class UserRepositoryAdapterIntegrationTest {

    @Autowired
    private UserOutputPort userOutputPort;

    @Autowired
    private UserUseCase userUseCase;

    // ─────────────────────────────────────────
    // save() → findById()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("save + findById: deve salvar e recuperar usuário do banco H2")
    void save_findById_salvaERecuperaUsuario() {
        User user = userUseCase.create("Pedro Davi", "pedro@email.com");

        Optional<User> resultado = userOutputPort.findById(user.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Pedro Davi",      resultado.get().getName());
        assertEquals("pedro@email.com", resultado.get().getEmail());
    }

    // ─────────────────────────────────────────
    // findAll()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("findAll: deve retornar todos os usuários")
    void findAll_retornaTodosOsUsuarios() {
        userUseCase.create("Pedro Davi",  "pedro@email.com");
        userUseCase.create("João Silva",  "joao@email.com");
        userUseCase.create("Maria Lima",  "maria@email.com");

        List<User> usuarios = userOutputPort.findAll();

        assertEquals(3, usuarios.size());
    }

    // ─────────────────────────────────────────
    // existsByEmail()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("existsByEmail: deve retornar true para email existente")
    void existsByEmail_emailExistente_retornaTrue() {
        userUseCase.create("Pedro Davi", "pedro@email.com");

        assertTrue(userOutputPort.existsByEmail("pedro@email.com"));
    }

    @Test
    @DisplayName("existsByEmail: deve retornar false para email inexistente")
    void existsByEmail_emailInexistente_retornaFalse() {
        assertFalse(userOutputPort.existsByEmail("naoexiste@email.com"));
    }

    // ─────────────────────────────────────────
    // update() — use case → adapter → H2
    // ─────────────────────────────────────────

    @Test
    @DisplayName("update: deve atualizar usuário no banco H2")
    void update_atualizaUsuarioNoBanco() {
        User user = userUseCase.create("Pedro", "pedro@email.com");

        userUseCase.update(user.getId(), "Pedro Davi", "pedrodavi@email.com");

        Optional<User> resultado = userOutputPort.findById(user.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Pedro Davi",          resultado.get().getName());
        assertEquals("pedrodavi@email.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("update: deve permitir manter o mesmo email")
    void update_mesmoEmail_atualizaSemErro() {
        User user = userUseCase.create("Pedro", "pedro@email.com");

        assertDoesNotThrow(() ->
                userUseCase.update(user.getId(), "Pedro Davi", "pedro@email.com"));
    }

    // ─────────────────────────────────────────
    // delete() — use case → adapter → H2
    // ─────────────────────────────────────────

    @Test
    @DisplayName("delete: deve remover usuário do banco H2")
    void delete_removeUsuarioDoBanco() {
        User user = userUseCase.create("Pedro Davi", "pedro@email.com");

        userUseCase.delete(user.getId());

        Optional<User> resultado = userOutputPort.findById(user.getId());
        assertFalse(resultado.isPresent());
    }

    // ─────────────────────────────────────────
    // Fluxo completo use case → adapter → H2
    // ─────────────────────────────────────────

    @Test
    @DisplayName("fluxo completo: create → findById → update → delete")
    void fluxoCompleto_createFindUpdateDelete() {
        // Create
        User user = userUseCase.create("Pedro Davi", "pedro@email.com");
        assertNotNull(user.getId());

        // FindById
        Optional<User> encontrado = userOutputPort.findById(user.getId());
        assertTrue(encontrado.isPresent());

        // Update
        userUseCase.update(user.getId(), "Pedro Davi Silva", "novoemail@email.com");
        Optional<User> atualizado = userOutputPort.findById(user.getId());
        assertEquals("Pedro Davi Silva", atualizado.get().getName());

        // Delete
        userUseCase.delete(user.getId());
        assertFalse(userOutputPort.findById(user.getId()).isPresent());
    }
}

