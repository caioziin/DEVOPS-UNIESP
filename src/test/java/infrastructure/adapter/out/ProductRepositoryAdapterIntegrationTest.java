package infrastructure.adapter.out;

import com.uniesp.application.port.out.ProductOutputPort;
import com.uniesp.application.useCase.ProductUseCase;
import com.uniesp.domain.model.Product;
import com.uniesp.infrastructure.adapter.out.ProductRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({ProductRepositoryAdapter.class, ProductUseCase.class})
@DisplayName("ProductRepositoryAdapter - Testes de Integração com H2")
class ProductRepositoryAdapterIntegrationTest {

    @Autowired
    private ProductOutputPort productOutputPort;

    @Autowired
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        // H2 limpa automaticamente entre os testes com @DataJpaTest
    }

    // ─────────────────────────────────────────
    // save() → findById()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("save + findById: deve salvar e recuperar produto do banco H2")
    void save_findById_salvaeRecuperaProduto() {
        Product produto = productUseCase.create("Notebook", new BigDecimal("1000.00"));

        Optional<Product> resultado = productOutputPort.findById(produto.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Notebook", resultado.get().getName());
        assertEquals(new BigDecimal("1000.00"), resultado.get().getPrice());
    }

    // ─────────────────────────────────────────
    // findAll()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("findAll: deve retornar todos os produtos ordenados por nome")
    void findAll_retornaProdutosOrdenadosPorNome() {
        productUseCase.create("Mouse", new BigDecimal("150.00"));
        productUseCase.create("Notebook", new BigDecimal("1000.00"));
        productUseCase.create("Teclado", new BigDecimal("300.00"));

        List<Product> produtos = productOutputPort.findAll();

        assertEquals(3, produtos.size());
        assertEquals("Mouse",    produtos.get(0).getName());
        assertEquals("Notebook", produtos.get(1).getName());
        assertEquals("Teclado",  produtos.get(2).getName());
    }

    // ─────────────────────────────────────────
    // existsByName()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("existsByName: deve retornar true para produto existente")
    void existsByName_produtoExistente_retornaTrue() {
        productUseCase.create("Notebook", new BigDecimal("1000.00"));

        assertTrue(productOutputPort.existsByName("Notebook"));
    }

    @Test
    @DisplayName("existsByName: deve retornar false para produto inexistente")
    void existsByName_produtoInexistente_retornaFalse() {
        assertFalse(productOutputPort.existsByName("Monitor"));
    }

    // ─────────────────────────────────────────
    // update() — use case → adapter → H2
    // ─────────────────────────────────────────

    @Test
    @DisplayName("update: deve atualizar produto no banco H2")
    void update_atualizaProdutoNoBanco() {
        Product produto = productUseCase.create("Notebook", new BigDecimal("1000.00"));

        Product atualizado = productUseCase.update(produto.getId(), "Notebook Pro", new BigDecimal("1500.00"));

        Optional<Product> resultado = productOutputPort.findById(produto.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Notebook Pro",        resultado.get().getName());
        assertEquals(new BigDecimal("1500.00"), resultado.get().getPrice());
    }

    // ─────────────────────────────────────────
    // delete() — use case → adapter → H2
    // ─────────────────────────────────────────

    @Test
    @DisplayName("delete: deve remover produto do banco H2")
    void delete_removeProdutoDoBanco() {
        Product produto = productUseCase.create("Notebook", new BigDecimal("1000.00"));

        productUseCase.delete(produto.getId());

        Optional<Product> resultado = productOutputPort.findById(produto.getId());
        assertFalse(resultado.isPresent());
    }

    // ─────────────────────────────────────────
    // Fluxo completo use case → adapter → H2
    // ─────────────────────────────────────────

    @Test
    @DisplayName("fluxo completo: create → findById → update → delete")
    void fluxoCompleto_createFindUpdateDelete() {
        // Create
        Product produto = productUseCase.create("Notebook", new BigDecimal("1000.00"));
        assertNotNull(produto.getId());

        // FindById
        Optional<Product> encontrado = productOutputPort.findById(produto.getId());
        assertTrue(encontrado.isPresent());

        // Update
        productUseCase.update(produto.getId(), "Notebook Pro", new BigDecimal("1500.00"));
        Optional<Product> atualizado = productOutputPort.findById(produto.getId());
        assertEquals("Notebook Pro", atualizado.get().getName());

        // Delete
        productUseCase.delete(produto.getId());
        assertFalse(productOutputPort.findById(produto.getId()).isPresent());
    }
}

