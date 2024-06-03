package com.aminkbi.learnspring.services;

import com.aminkbi.learnspring.dtos.supplier.SupplierDTO;
import com.aminkbi.learnspring.dtos.supplier.SupplierResponseDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Supplier;
import com.aminkbi.learnspring.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSupplier() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setName("Tesla Supplier");
        supplier.setContactEmail("contact@tesla.com");
        supplier.setContactName("Elon Musk");
        supplier.setContactPhone("123456789");

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        SupplierDTO supplierDTO = new SupplierDTO();
        supplier.setName("Tesla Supplier");
        supplier.setContactEmail("contact@tesla.com");
        supplier.setContactName("Elon Musk");
        supplier.setContactPhone("123456789");
        // When
        SupplierResponseDTO savedSupplier = supplierService.addSupplier(supplierDTO);

        // Then
        assertNotNull(savedSupplier);
        assertEquals("Tesla Supplier", savedSupplier.getName());
        assertEquals("contact@tesla.com", savedSupplier.getContactEmail());
        assertEquals("Elon Musk", savedSupplier.getContactName());
        assertEquals("123456789", savedSupplier.getContactPhone());

        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testGetSupplierById() {
        // Given
        Long supplierId = 1L;
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        supplier.setName("Tesla Supplier");
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));

        // When
        SupplierResponseDTO foundSupplier = supplierService.getSupplierById(supplierId);

        // Then
        assertEquals(supplierId, foundSupplier.getId());
        assertEquals("Tesla Supplier", foundSupplier.getName());

        verify(supplierRepository, times(1)).findById(supplierId);
    }

    @Test
    void testUpdateSupplier() {
        // Given
        Long supplierId = 1L;
        SupplierDTO updatedSupplier = new SupplierDTO();
        updatedSupplier.setName("Updated Tesla Supplier");
        updatedSupplier.setContactEmail("updated@tesla.com");
        updatedSupplier.setContactName("New Contact");
        updatedSupplier.setContactPhone("987654321");

        Supplier existingSupplier = new Supplier();
        existingSupplier.setId(supplierId);
        existingSupplier.setName("Tesla Supplier");

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(existingSupplier);

        // When
        SupplierResponseDTO supplierResponseDTO = supplierService.updateSupplier(supplierId, updatedSupplier);
        // Then
        assertNotNull(supplierResponseDTO);
        assertEquals("Updated Tesla Supplier", supplierResponseDTO.getName());
        assertEquals("updated@tesla.com", supplierResponseDTO.getContactEmail());
        assertEquals("New Contact", supplierResponseDTO.getContactName());
        assertEquals("987654321", supplierResponseDTO.getContactPhone());

        verify(supplierRepository, times(1)).findById(supplierId);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testDeleteSupplierById() {
        // Given
        Long supplierId = 1L;

        // When
        supplierService.deleteSupplierById(supplierId);

        // Then
        verify(supplierRepository, times(1)).deleteById(supplierId);
    }
}
