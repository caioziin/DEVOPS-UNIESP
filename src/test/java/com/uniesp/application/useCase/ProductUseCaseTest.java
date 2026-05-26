package com.uniesp.application.useCase;

import com.uniesp.application.port.out.ProductOutputPort;
import com.uniesp.domain.exception.BusinessException;
import com.uniesp.domain.exception.ResourceNotFoundException;
import com.uniesp.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductUseCase - Testes com Mockito")
class ProductUseCaseTest {

    @Mock
    private ProductOutputPort productOutputPort;

    @InjectMocks
    private ProductUseCase productUseCase;

    // ─────────────────────────────────────────
    // findAll()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("findAll: deve retornar lista de produtos")
    void findAll_retornaListaDeProdutos() {
        List<Product> produtos = List.of(
                new Product("Notebook", new BigDecimal("1000.00")),
                new Product("Mouse", new BigDecimal("150.00"))
        );
        when(productOutputPort.findAll()).thenReturn(produtos);

        List<Product> resultado = productUseCase.findAll();

        assertEquals(2, resultado.size());
        verify(productOutputPort, times(1)).findAll();
    }

    // ─────────────────────────────────────────
    // findById()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("findById: deve retornar produto quando encontrado")
    void findById_produtoExistente_retornaProduto() {
        Product produto = new Product(1L, "Notebook", new BigDecimal("1000.00"), null);
        when(productOutputPort.findById(1L)).thenReturn(Optional.of(produto));

        Product resultado = productUseCase.findById(1L);

        assertEquals("Notebook", resultado.getName());
        verify(productOutputPort, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById: deve lançar ResourceNotFoundException quando não encontrado")
    void findById_produtoNaoExistente_lancaExcecao() {
        when(productOutputPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productUseCase.findById(99L));
    }

    // ─────────────────────────────────────────
    // create()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("create: deve criar produto quando nome não existe")
    void create_nomeDisponivel_criaProduto() {
        Product produto = new Product("Teclado", new BigDecimal("300.00"));
        when(productOutputPort.existsByName("Teclado")).thenReturn(false);
        when(productOutputPort.save(any(Product.class))).thenReturn(produto);

        Product resultado = productUseCase.create("Teclado", new BigDecimal("300.00"));

        assertEquals("Teclado", resultado.getName());
        verify(productOutputPort, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("create: deve lançar BusinessException quando nome já existe")
    void create_nomeDuplicado_lancaExcecao() {
        when(productOutputPort.existsByName("Notebook")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> productUseCase.create("Notebook", new BigDecimal("1000.00")));

        assertTrue(ex.getMessage().contains("Notebook"));
        verify(productOutputPort, never()).save(any());
    }

    // ─────────────────────────────────────────
    // update()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("update: deve atualizar produto existente")
    void update_produtoExistente_atualizaDados() {
        Product produto = new Product(1L, "Notebook", new BigDecimal("1000.00"), null);
        when(productOutputPort.findById(1L)).thenReturn(Optional.of(produto));
        when(productOutputPort.save(any(Product.class))).thenReturn(produto);

        Product resultado = productUseCase.update(1L, "Notebook Pro", new BigDecimal("1500.00"));

        assertEquals("Notebook Pro", resultado.getName());
        verify(productOutputPort, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("update: deve lançar ResourceNotFoundException para produto inexistente")
    void update_produtoInexistente_lancaExcecao() {
        when(productOutputPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productUseCase.update(99L, "Notebook", new BigDecimal("1000.00")));
    }

    // ─────────────────────────────────────────
    // delete()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("delete: deve deletar produto existente")
    void delete_produtoExistente_deletaComSucesso() {
        Product produto = new Product(1L, "Notebook", new BigDecimal("1000.00"), null);
        when(productOutputPort.findById(1L)).thenReturn(Optional.of(produto));

        assertDoesNotThrow(() -> productUseCase.delete(1L));
        verify(productOutputPort, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete: deve lançar ResourceNotFoundException para produto inexistente")
    void delete_produtoInexistente_lancaExcecao() {
        when(productOutputPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> productUseCase.delete(99L));
        verify(productOutputPort, never()).deleteById(any());
    }

    // ─────────────────────────────────────────
    // calcularPrecoFinal()
    // ─────────────────────────────────────────

    @Test
    @DisplayName("calcularPrecoFinal: notebook deve ter 10% de desconto")
    void calcularPrecoFinal_notebook_aplicaDesconto() {
        Product produto = new Product(1L, "Notebook", new BigDecimal("1000.00"), null);
        when(productOutputPort.findById(1L)).thenReturn(Optional.of(produto));

        BigDecimal resultado = productUseCase.calcularPrecoFinal(1L);

        assertEquals(new BigDecimal("900.00"), resultado);
    }
}