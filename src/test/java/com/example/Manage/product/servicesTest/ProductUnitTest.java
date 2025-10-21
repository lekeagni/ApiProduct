package com.example.Manage.product.servicesTest;


import com.example.Manage.product.dto.ProductDTO;
import com.example.Manage.product.exception.ProductAlreadyExistException;
import com.example.Manage.product.exception.ProductNotFoundException;
import com.example.Manage.product.mapper.ProductMapper;
import com.example.Manage.product.model.Product;
import com.example.Manage.product.repository.ProductRepositoy;
import com.example.Manage.product.service.ServiceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProductUnitTest {

    @Mock
    private ProductRepositoy productRepositoy;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);

         product = new Product(5,"savon","menage",300.0);
         productDTO = new ProductDTO(5,"savon","menage",300.0);
    }

    @Test
    @DisplayName("creation d'un nouveau produit")
    void ShouldCreateProduct(){
        when(productRepositoy.findByName(productDTO.getName())).thenReturn(Optional.empty());
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepositoy.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("savon");
        verify(productRepositoy, times(1)).save(product);

    }

    @Test
    @DisplayName("Échec de la création : utilisateur existe déjà")
    void shouldThrowExceptionIfProductAlreadyExists() {
        when(productRepositoy.findByName(productDTO.getName())).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> productService.createProduct(productDTO))
                .isInstanceOf(ProductAlreadyExistException.class)
                .hasMessageContaining("savon");

        verify(productRepositoy, never()).save(any());
    }

    @Test
    @DisplayName("Récupérer tous les utilisateurs")
    void shouldGetAllProducts() {
        List<Product> models = Arrays.asList(product);

        when(productRepositoy.findAll()).thenReturn(models);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        List<ProductDTO> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("savon");
    }

    @Test
    @DisplayName("Récupérer un utilisateur par ID")
    void shouldGetProductById() {
        when(productRepositoy.findById(1)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(1);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("savon");
    }

    @Test
    @DisplayName("Échec de récupération : utilisateur introuvable")
    void shouldThrowExceptionIfProductNotFound() {
        when(productRepositoy.findById(2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(2))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("2");
    }

    @Test
    @DisplayName("Mettre à jour un produit")
    void shouldUpdateProduct() {
        when(productRepositoy.findById(1)).thenReturn(Optional.of(product));
        when(productRepositoy.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(1, productDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("savon");
    }

    @Test
    @DisplayName("Échec de mise à jour : utilisateur introuvable")
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {
        when(productRepositoy.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(99, productDTO))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("99");

        verify(productRepositoy, never()).save(any());
    }

    @Test
    @DisplayName("Supprimer un utilisateur existant")
    void shouldDeleteProduct() {
        when(productRepositoy.findById(1)).thenReturn(Optional.of(product));

        Boolean result = productService.deleteProduct(1);

        assertThat(result).isTrue();
        verify(productRepositoy).delete(product);
    }

    @Test
    @DisplayName("Supprimer un utilisateur inexistant")
    void shouldNotDeleteNonExistingProduct() {
        when(productRepositoy.findById(2)).thenReturn(Optional.empty());

        Boolean result = productService.deleteProduct(2);

        assertThat(result).isFalse();
        verify(productRepositoy, never()).delete(any());
    }
}
