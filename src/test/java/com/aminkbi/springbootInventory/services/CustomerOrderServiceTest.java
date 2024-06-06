package com.aminkbi.springbootInventory.services;

import com.aminkbi.springbootInventory.dtos.customerOrder.CustomerOrderDTO;
import com.aminkbi.springbootInventory.dtos.customerOrder.CustomerOrderResponseDTO;
import com.aminkbi.springbootInventory.models.AppUser;
import com.aminkbi.springbootInventory.models.CustomerOrder;
import com.aminkbi.springbootInventory.repositories.CustomerOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerOrderServiceTest {

    @Mock
    private CustomerOrderRepository customerOrderRepository;

    @InjectMocks
    private CustomerOrderService customerOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomerOrder() {
        // Given
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setTotal(1000.0F);
        customerOrder.setStatus("Shipped");
        customerOrder.setOrderDate(LocalDateTime.now());
        AppUser user = new AppUser();
        user.setId(1L);
        customerOrder.setUser(user);

        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(customerOrder);

        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setStatus("Shipped");
        customerOrderDTO.setTotal(1000.0F);
        AppUser user2 = new AppUser();
        user2.setId(1L);
        customerOrder.setUser(user);

        // When
        CustomerOrderResponseDTO savedCustomerOrder = customerOrderService.addCustomerOrder(customerOrderDTO);

        // Then
        assertNotNull(savedCustomerOrder);
        assertEquals("Shipped", savedCustomerOrder.getStatus());
        assertEquals(1000.0F, savedCustomerOrder.getTotal());
        assertNotNull(savedCustomerOrder.getOrderDate());
        assertEquals(user, savedCustomerOrder.getUser());

        verify(customerOrderRepository, times(1)).save(any(CustomerOrder.class));
    }

    @Test
    void testGetCustomerOrderById() {
        // Given
        Long customerOrderId = 1L;
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(customerOrderId);
        customerOrder.setTotal(1000.0F);
        customerOrder.setStatus("Shipped");
        customerOrder.setOrderDate(LocalDateTime.now());
        AppUser user = new AppUser();
        user.setId(1L);
        customerOrder.setUser(user);

        when(customerOrderRepository.findById(customerOrderId)).thenReturn(Optional.of(customerOrder));

        // When
        CustomerOrderResponseDTO foundCustomerOrder = customerOrderService.getCustomerOrderById(customerOrderId);

        // Then
        assertEquals(customerOrderId, foundCustomerOrder.getId());
        assertEquals(1000.0F, foundCustomerOrder.getTotal());
        assertEquals("Shipped", foundCustomerOrder.getStatus());
        assertNotNull(foundCustomerOrder.getOrderDate());
        assertEquals(user, foundCustomerOrder.getUser());

        verify(customerOrderRepository, times(1)).findById(customerOrderId);
    }

    @Test
    void testUpdateCustomerOrder() {
        // Given
        Long customerOrderId = 1L;
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO();
        customerOrderDTO.setTotal(2000.0F);
        customerOrderDTO.setStatus("Delivered");
        customerOrderDTO.setUserId(2L);

        CustomerOrder existingCustomerOrder = new CustomerOrder();
        existingCustomerOrder.setId(customerOrderId);
        existingCustomerOrder.setTotal(1000.0F);
        existingCustomerOrder.setStatus("Shipped");
        existingCustomerOrder.setOrderDate(LocalDateTime.now());
        AppUser oldUser = new AppUser();
        oldUser.setId(1L);
        existingCustomerOrder.setUser(oldUser);

        CustomerOrder updatedCustomerOrder = new CustomerOrder();
        updatedCustomerOrder.setId(customerOrderId);
        updatedCustomerOrder.setTotal(2000.0F);
        updatedCustomerOrder.setStatus("Delivered");
        updatedCustomerOrder.setOrderDate(LocalDateTime.now());
        AppUser newUser = new AppUser();
        newUser.setId(2L);
        existingCustomerOrder.setUser(newUser);

        when(customerOrderRepository.findById(customerOrderId)).thenReturn(Optional.of(existingCustomerOrder));
        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(updatedCustomerOrder);

        // When
        CustomerOrderResponseDTO customerOrderResponseDTO = customerOrderService.updateCustomerOrder(customerOrderId, customerOrderDTO);

        // Then
        assertNotNull(customerOrderResponseDTO);
        assertEquals(2000.0F, customerOrderResponseDTO.getTotal());
        assertEquals("Delivered", customerOrderResponseDTO.getStatus());

        verify(customerOrderRepository, times(1)).findById(customerOrderId);
        verify(customerOrderRepository, times(1)).save(any(CustomerOrder.class));
    }

    @Test
    void testDeleteCustomerOrderById() {
        // Given
        Long customerOrderId = 1L;

        // When
        when(customerOrderRepository.existsById(customerOrderId)).thenReturn(true);
        customerOrderService.deleteCustomerOrderById(customerOrderId);

        // Then
        verify(customerOrderRepository, times(1)).existsById(customerOrderId);
        verify(customerOrderRepository, times(1)).deleteById(customerOrderId);
    }



    @Test
    void testGetAllCustomerOrders() {
        // Given
        CustomerOrder customerOrder1 = new CustomerOrder();
        customerOrder1.setId(1L);
        customerOrder1.setTotal(500.0F);
        customerOrder1.setStatus("Shipped");
        customerOrder1.setOrderDate(LocalDateTime.now());
        AppUser user1 = new AppUser();
        user1.setId(1L);
        customerOrder1.setUser(user1);

        CustomerOrder customerOrder2 = new CustomerOrder();
        customerOrder2.setId(2L);
        customerOrder2.setTotal(1000.0F);
        customerOrder2.setStatus("Delivered");
        customerOrder2.setOrderDate(LocalDateTime.now());
        AppUser user2 = new AppUser();
        user2.setId(2L);
        customerOrder2.setUser(user2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<CustomerOrder> page = new PageImpl<>(Arrays.asList(customerOrder1, customerOrder2));

        when(customerOrderRepository.findAll(pageable)).thenReturn(page);

        // When
        List<CustomerOrderResponseDTO> result = customerOrderService.getAllCustomOrders(0, 10);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(500.0F, result.get(0).getTotal());
        assertEquals("Shipped", result.get(0).getStatus());
        assertEquals(user1, result.get(0).getUser());
        assertEquals(1000.0F, result.get(1).getTotal());
        assertEquals("Delivered", result.get(1).getStatus());
        assertEquals(user2, result.get(1).getUser());
    }
}
