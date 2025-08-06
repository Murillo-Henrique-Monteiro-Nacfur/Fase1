package com.postech.fiap.fase1.core.gateway.product;

import com.postech.fiap.fase1.core.domain.model.ProductDomain;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para ProductGateway.
 * Utiliza Mockito para simular as dependências (DataSource, ProductPresenter)
 * e AssertJ para as asserções.
 */
@ExtendWith(MockitoExtension.class)
class ProductGatewayTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private ProductPresenter productPresenter;

    @InjectMocks
    private ProductGateway productGateway;


    private ProductDomain productDomain;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        // Configuração inicial para os testes
        productDomain = new ProductDomain();
        productDomain.setId(1L);
        productDomain.setName("Test Product");

        productDTO = ProductDTO.builder().build();
    }

    @Test
    @DisplayName("Deve construir uma instância de ProductGateway com sucesso")
    void build_shouldReturnProductGatewayInstance() {
        // Ação
        ProductGateway gateway = ProductGateway.build(dataSource);

        // Verificação
        assertThat(gateway).isNotNull().isInstanceOf(ProductGateway.class);
    }

    @Test
    @DisplayName("Deve retornar um produto quando getProductById é chamado com um ID válido")
    void getProductById_whenIdIsValid_shouldReturnProductDomain() {
        // Cenário (Given)
        when(dataSource.getById(1L)).thenReturn(productDTO);
        when(productPresenter.toDomain(any())).thenReturn(productDomain);

        // Ação (When)
        ProductDomain result = productGateway.getProductById(1L);

        // Verificação (Then)
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Product");

        // Verifica se os métodos corretos foram chamados
        verify(dataSource).getById(1L);
        verify(productPresenter).toDomain(productDTO);
    }

    @Test
    @DisplayName("Deve retornar uma lista de produtos para um restaurante")
    void getProductByIdRestaurant_whenProductsExist_shouldReturnProductDomainList() {
        // Cenário (Given)
        Long restaurantId = 10L;
        List<ProductDTO> dtoList = List.of(productDTO);
        when(dataSource.getProductByIdRestaurant(restaurantId)).thenReturn(dtoList);
        when(productPresenter.toDomain(any())).thenReturn(productDomain);

        // Ação (When)
        List<ProductDomain> result = productGateway.getProductByIdRestaurant(restaurantId);

        // Verificação (Then)
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0)).isEqualTo(productDomain);

        verify(dataSource).getProductByIdRestaurant(restaurantId);
        verify(productPresenter, times(1)).toDomain(productDTO);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia se o restaurante não tiver produtos")
    void getProductByIdRestaurant_whenNoProducts_shouldReturnEmptyList() {
        // Cenário (Given)
        Long restaurantId = 20L;
        when(dataSource.getProductByIdRestaurant(restaurantId)).thenReturn(Collections.emptyList());

        // Ação (When)
        List<ProductDomain> result = productGateway.getProductByIdRestaurant(restaurantId);

        // Verificação (Then)
        assertThat(result).isNotNull().isEmpty();

        verify(dataSource).getProductByIdRestaurant(restaurantId);
        verify(productPresenter, never()).toDomain(any()); // Garante que o presenter não é chamado
    }


    @Test
    @DisplayName("Deve criar um novo produto com sucesso")
    void create_shouldCreateAndReturnProductDomain() {
        // Cenário (Given)
        when(productPresenter.toDTO(productDomain)).thenReturn(productDTO);
        when(dataSource.createProduct(any())).thenReturn(productDTO);
        when(productPresenter.toDomain(any())).thenReturn(productDomain);

        // Ação (When)
        ProductDomain createdProduct = productGateway.create(productDomain);

        // Verificação (Then)
        assertThat(createdProduct).isNotNull().isEqualTo(productDomain);

        // Verifica a ordem das interações
        verify(productPresenter).toDTO(productDomain);
        verify(dataSource).createProduct(any());
        verify(productPresenter).toDomain(any());
    }

    @Test
    @DisplayName("Deve chamar o método de exclusão do dataSource ao deletar um produto")
    void delete_shouldCallDataSourceDeleteMethod() {
        // Cenário (Given)
        // O método deleteProductById retorna void, então não precisamos de when().thenReturn()
        doNothing().when(dataSource).deleteProductById(1L);

        // Ação (When)
        productGateway.delete(productDomain);

        // Verificação (Then)
        // Apenas verificamos se o método foi chamado com o ID correto
        verify(dataSource).deleteProductById(1L);
    }
}