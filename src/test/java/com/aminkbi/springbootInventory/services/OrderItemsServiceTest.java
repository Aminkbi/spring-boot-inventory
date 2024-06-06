package com.aminkbi.springbootInventory.services;

import com.aminkbi.springbootInventory.dtos.orderItem.OrderItemsDTO;
import com.aminkbi.springbootInventory.dtos.orderItem.OrderItemsResponseDTO;
import com.aminkbi.springbootInventory.models.CustomerOrder;
import com.aminkbi.springbootInventory.models.OrderItems;
import com.aminkbi.springbootInventory.models.Product;
import com.aminkbi.springbootInventory.repositories.OrderItemsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderItemsServiceTest {

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private OrderItemsService orderItemsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrderItems() {
        // Given
        OrderItems orderItems = new OrderItems();
        orderItems.setPrice(1000.0);
        orderItems.setQuantity(10);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(2L);
        orderItems.setCustomerOrder(customerOrder);

        Product product = new Product();
        product.setId(1L);
        orderItems.setProduct(product);

        when(orderItemsRepository.save(any(OrderItems.class))).thenReturn(orderItems);

        OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
        orderItemsDTO.setPrice(1000.0);
        orderItemsDTO.setQuantity(10);
        orderItemsDTO.setCustomerOrderId(2L);
        orderItemsDTO.setProductId(1L);

        // When
        OrderItemsResponseDTO savedOrderItems = orderItemsService.addOrderItem(orderItemsDTO);

        // Then
        assertNotNull(savedOrderItems);
        assertEquals(1000.0, savedOrderItems.getPrice());
        assertEquals(10, savedOrderItems.getQuantity());
        assertEquals(2L, savedOrderItems.getCustomerOrder().getId());
        assertEquals(1L, savedOrderItems.getProduct().getId());

        verify(orderItemsRepository, times(1)).save(any(OrderItems.class));
    }

    @Test
    void testGetOrderItemsById() {
        // Given
        Long orderItemsId = 1L;
        OrderItems orderItems = new OrderItems();
        orderItems.setId(orderItemsId);
        orderItems.setPrice(1000.0);
        orderItems.setQuantity(10);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(2L);
        orderItems.setCustomerOrder(customerOrder);

        Product product = new Product();
        product.setId(1L);
        orderItems.setProduct(product);

        when(orderItemsRepository.findById(orderItemsId)).thenReturn(Optional.of(orderItems));

        // When
        OrderItemsResponseDTO foundOrderItems = orderItemsService.getOrderItemById(orderItemsId);

        // Then
        assertNotNull(foundOrderItems);
        assertEquals(1000.0, foundOrderItems.getPrice());
        assertEquals(10, foundOrderItems.getQuantity());
        assertEquals(2L, foundOrderItems.getCustomerOrder().getId());
        assertEquals(1L, foundOrderItems.getProduct().getId());

        verify(orderItemsRepository, times(1)).findById(orderItemsId);
    }

    @Test
    void testUpdateOrderItems() {
        // Given
        Long orderItemsId = 1L;
        OrderItemsDTO orderItemsDTO = new OrderItemsDTO();
        orderItemsDTO.setPrice(2000.0);
        orderItemsDTO.setQuantity(100);
        orderItemsDTO.setCustomerOrderId(1L);
        orderItemsDTO.setProductId(2L);

        OrderItems existingOrderItems = new OrderItems();
        OrderItems orderItems = new OrderItems();
        orderItems.setId(orderItemsId);
        orderItems.setPrice(1000.0);
        orderItems.setQuantity(10);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(2L);
        orderItems.setCustomerOrder(customerOrder);

        Product product = new Product();
        product.setId(1L);
        orderItems.setProduct(product);

        OrderItems updatedOrderItems = new OrderItems();
        updatedOrderItems.setId(orderItemsId);
        updatedOrderItems.setPrice(2000.0);
        updatedOrderItems.setQuantity(100);
        CustomerOrder newCustomerOrder = new CustomerOrder();
        customerOrder.setId(1L);
        orderItems.setCustomerOrder(newCustomerOrder);

        Product newProduct = new Product();
        product.setId(2L);
        orderItems.setProduct(newProduct);

        when(orderItemsRepository.findById(orderItemsId)).thenReturn(Optional.of(existingOrderItems));
        when(orderItemsRepository.save(any(OrderItems.class))).thenReturn(updatedOrderItems);

        // When
        OrderItemsResponseDTO orderItemsResponseDTO = orderItemsService.updateOrderItem(orderItemsId, orderItemsDTO);

        // Then
        assertNotNull(orderItemsResponseDTO);
        assertEquals(2000.0, orderItemsResponseDTO.getPrice());
        assertEquals(100, orderItemsResponseDTO.getQuantity());

        verify(orderItemsRepository, times(1)).findById(orderItemsId);
        verify(orderItemsRepository, times(1)).save(any(OrderItems.class));
    }

    @Test
    void testDeleteOrderItemsById() {
        // Given
        Long orderItemsId = 1L;

        // Setup mock behavior
        when(orderItemsRepository.existsById(orderItemsId)).thenReturn(true);

        // When
        orderItemsService.deleteOrderItemById(orderItemsId);

        // Then
        verify(orderItemsRepository, times(1)).existsById(orderItemsId);
        verify(orderItemsRepository, times(1)).deleteById(orderItemsId);
    }

    @Test
    void testGetAllOrderItemss() {
        // Given
        OrderItems orderItems = new OrderItems();
        orderItems.setId(1L);
        orderItems.setPrice(1000.0);
        orderItems.setQuantity(10);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(2L);
        orderItems.setCustomerOrder(customerOrder);

        Product product = new Product();
        product.setId(1L);
        orderItems.setProduct(product);

        OrderItems orderItems2 = new OrderItems();
        orderItems2.setId(2L);
        orderItems2.setPrice(2000.0);
        orderItems2.setQuantity(20);
        CustomerOrder customerOrder2 = new CustomerOrder();
        customerOrder2.setId(2L);
        orderItems2.setCustomerOrder(customerOrder2);

        Product product2 = new Product();
        product2.setId(1L);
        orderItems2.setProduct(product2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderItems> page = new PageImpl<>(Arrays.asList(orderItems, orderItems2));

        when(orderItemsRepository.findAll(pageable)).thenReturn(page);

        // When
        List<OrderItemsResponseDTO> result = orderItemsService.getAllOrderItems(0, 10);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1000.0, result.get(0).getPrice());
        assertEquals(10, result.get(0).getQuantity());
        assertEquals(product, result.get(0).getProduct());
        assertEquals(2000.0, result.get(1).getPrice());
        assertEquals(20, result.get(1).getQuantity());
        assertEquals(product2, result.get(1).getProduct());
    }
}
