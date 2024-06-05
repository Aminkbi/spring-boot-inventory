package com.aminkbi.learnspring.services;

import com.aminkbi.learnspring.constants.stockEntries.ChangeTypes;
import com.aminkbi.learnspring.dtos.stockEntries.StockEntriesDTO;
import com.aminkbi.learnspring.dtos.stockEntries.StockEntriesResponseDTO;
import com.aminkbi.learnspring.models.StockEntries;
import com.aminkbi.learnspring.models.Product;
import com.aminkbi.learnspring.repositories.StockEntriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StockEntriesServiceTest {

    @Mock
    private StockEntriesRepository stockEntriesRepository;

    @InjectMocks
    private StockEntriesService stockEntriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStockEntries() {
        // Given
        StockEntries stockEntries = new StockEntries();
        stockEntries.setChangeQuantity(10);
        stockEntries.setChangeType(ChangeTypes.ADDITION);

        Product product = new Product();
        product.setId(1L);
        stockEntries.setProduct(product);

        when(stockEntriesRepository.save(any(StockEntries.class))).thenReturn(stockEntries);

        StockEntriesDTO stockEntriesDTO = new StockEntriesDTO();
        stockEntriesDTO.setChangeType(ChangeTypes.ADDITION);
        stockEntriesDTO.setChangeQuantity(10);
        stockEntriesDTO.setProductId(1L);

        // When
        StockEntriesResponseDTO savedStockEntries = stockEntriesService.addStockEntries(stockEntriesDTO);

        // Then
        assertNotNull(savedStockEntries);
        assertNotNull(savedStockEntries.getChangeDate());
        assertEquals(10, savedStockEntries.getChangeQuantity());
        assertEquals(1L, savedStockEntries.getProduct().getId());
        assertEquals(ChangeTypes.ADDITION, savedStockEntries.getChangeType());

        verify(stockEntriesRepository, times(1)).save(any(StockEntries.class));
    }

    @Test
    void testGetStockEntriesById() {
        // Given
        Long stockEntriesId = 1L;
        StockEntries stockEntries = new StockEntries();
        stockEntries.setId(stockEntriesId);
        stockEntries.setChangeType(ChangeTypes.REMOVAL);
        stockEntries.setChangeQuantity(10);

        Product product = new Product();
        product.setId(1L);
        stockEntries.setProduct(product);

        when(stockEntriesRepository.findById(stockEntriesId)).thenReturn(Optional.of(stockEntries));

        // When
        StockEntriesResponseDTO foundStockEntries = stockEntriesService.getStockEntriesById(stockEntriesId);

        // Then
        assertNotNull(foundStockEntries);
        assertNotNull(foundStockEntries.getChangeDate());
        assertEquals(10, foundStockEntries.getChangeQuantity());
        assertEquals(1L, foundStockEntries.getProduct().getId());
        assertEquals(ChangeTypes.REMOVAL, foundStockEntries.getChangeType());

        verify(stockEntriesRepository, times(1)).findById(stockEntriesId);
    }

    @Test
    void testUpdateStockEntries() {
        // Given
        Long stockEntriesId = 1L;
        StockEntriesDTO stockEntriesDTO = new StockEntriesDTO();
        stockEntriesDTO.setChangeType(ChangeTypes.REMOVAL);
        stockEntriesDTO.setChangeQuantity(100);
        stockEntriesDTO.setProductId(2L);

        StockEntries existingStockEntries = new StockEntries();
        StockEntries stockEntries = new StockEntries();
        stockEntries.setId(stockEntriesId);
        stockEntries.setChangeQuantity(99);
        stockEntries.setChangeType(ChangeTypes.REMOVAL);
        Product product = new Product();
        product.setId(2L);
        stockEntries.setProduct(product);

        StockEntries updatedStockEntries = new StockEntries();
        updatedStockEntries.setId(stockEntriesId);
        updatedStockEntries.setChangeType(ChangeTypes.REMOVAL);
        updatedStockEntries.setChangeQuantity(100);


        Product newProduct = new Product();
        product.setId(2L);
        stockEntries.setProduct(newProduct);

        when(stockEntriesRepository.findById(stockEntriesId)).thenReturn(Optional.of(existingStockEntries));
        when(stockEntriesRepository.save(any(StockEntries.class))).thenReturn(updatedStockEntries);

        // When
        StockEntriesResponseDTO stockEntriesResponseDTO = stockEntriesService.updateStockEntries(stockEntriesId, stockEntriesDTO);

        // Then
        assertNotNull(stockEntriesResponseDTO);
        assertEquals(ChangeTypes.REMOVAL, stockEntriesResponseDTO.getChangeType());
        assertEquals(100, stockEntriesResponseDTO.getChangeQuantity());

        verify(stockEntriesRepository, times(1)).findById(stockEntriesId);
        verify(stockEntriesRepository, times(1)).save(any(StockEntries.class));
    }

    @Test
    void testDeleteStockEntriesById() {
        // Given
        Long stockEntriesId = 1L;

        // Setup mock behavior
        when(stockEntriesRepository.existsById(stockEntriesId)).thenReturn(true);

        // When
        stockEntriesService.deleteStockEntriesById(stockEntriesId);

        // Then
        verify(stockEntriesRepository, times(1)).existsById(stockEntriesId);
        verify(stockEntriesRepository, times(1)).deleteById(stockEntriesId);
    }

    @Test
    void testGetAllStockEntries() {
        // Given
        StockEntries stockEntries = new StockEntries();
        stockEntries.setId(1L);
        stockEntries.setChangeQuantity(50);
        stockEntries.setChangeType(ChangeTypes.ADDITION);
        Product product = new Product();
        product.setId(2L);
        stockEntries.setProduct(product);


        StockEntries stockEntries2 = new StockEntries();
        stockEntries2.setId(1L);
        stockEntries2.setChangeQuantity(40);
        stockEntries2.setChangeType(ChangeTypes.REMOVAL);
        Product product2 = new Product();
        product2.setId(2L);
        stockEntries2.setProduct(product2);


        Pageable pageable = PageRequest.of(0, 10);
        Page<StockEntries> page = new PageImpl<>(Arrays.asList(stockEntries, stockEntries2));

        when(stockEntriesRepository.findAll(pageable)).thenReturn(page);

        // When
        List<StockEntriesResponseDTO> result = stockEntriesService.getAllStockEntries(0, 10);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(50, result.get(0).getChangeQuantity());
        assertEquals(ChangeTypes.ADDITION, result.get(0).getChangeType());
        assertEquals(product, result.get(0).getProduct());
        assertEquals(40, result.get(1).getChangeQuantity());
        assertEquals(ChangeTypes.REMOVAL, result.get(1).getChangeType());
        assertEquals(product2, result.get(1).getProduct());
    }
}
