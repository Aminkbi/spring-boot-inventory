package com.aminkbi.learnspring.services;

import com.aminkbi.learnspring.dtos.product.ProductDTO;
import com.aminkbi.learnspring.models.Category;
import com.aminkbi.learnspring.models.Product;
import com.aminkbi.learnspring.models.Supplier;
import com.aminkbi.learnspring.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Tesla Model S");
        productDTO.setPrice(25000.0);
        productDTO.setQuantity(10);
        productDTO.setDescription("Quickest electric sedan");
        productDTO.setCategoryId(1L);
        productDTO.setSupplierId(2L);

        Product product = new Product();
        product.setName("Tesla Model S");
        product.setPrice(25000.0);
        product.setQuantity(10);
        product.setDescription("Quickest electric sedan");

        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);

        Supplier supplier = new Supplier();
        supplier.setId(2L);
        product.setSupplier(supplier);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Product savedProduct = productService.addProduct(productDTO);

        // Then
        assertNotNull(savedProduct);
        assertEquals("Tesla Model S", savedProduct.getName());
        assertEquals(25000.0, savedProduct.getPrice());
        assertEquals(10, savedProduct.getQuantity());
        assertEquals("Quickest electric sedan", savedProduct.getDescription());
        assertEquals(1L, savedProduct.getCategory().getId());
        assertEquals(2L, savedProduct.getSupplier().getId());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetProductById() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Tesla Model S");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        Optional<Product> foundProduct = productService.getProductById(productId);

        // Then
        assertTrue(foundProduct.isPresent());
        assertEquals(productId, foundProduct.get().getId());
        assertEquals("Tesla Model S", foundProduct.get().getName());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testUpdateProduct() {
        // Given
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Tesla Model X");
        productDTO.setPrice(30000.0);
        productDTO.setQuantity(15);
        productDTO.setDescription("Luxury electric SUV");
        productDTO.setCategoryId(1L);
        productDTO.setSupplierId(2L);

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Tesla Model S");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // When
        Product updatedProduct = productService.updateProduct(productId, productDTO);

        // Then
        assertNotNull(updatedProduct);
        assertEquals("Tesla Model X", updatedProduct.getName());
        assertEquals(30000.0, updatedProduct.getPrice());
        assertEquals(15, updatedProduct.getQuantity());
        assertEquals("Luxury electric SUV", updatedProduct.getDescription());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProductById() {
        // Given
        Long productId = 1L;

        // When
        productService.deleteProductById(productId);

        // Then
        verify(productRepository, times(1)).deleteById(productId);
    }
}
