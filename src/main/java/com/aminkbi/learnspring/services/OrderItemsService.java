package com.aminkbi.learnspring.services;


import com.aminkbi.learnspring.dtos.orderItem.OrderItemsDTO;
import com.aminkbi.learnspring.dtos.orderItem.OrderItemsResponseDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.CustomerOrder;
import com.aminkbi.learnspring.models.OrderItems;
import com.aminkbi.learnspring.models.Product;
import com.aminkbi.learnspring.repositories.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderItemsService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    public OrderItemsResponseDTO addOrderItem(OrderItemsDTO orderItemsDTO) {
        OrderItems orderItems = orderItemsRepository.save(this.mapToOrderItems(orderItemsDTO));
        return mapToDTO(orderItems);
    }

    public OrderItemsResponseDTO getOrderItemById(Long id) {
        OrderItems orderItems = orderItemsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("OrderItem not found with id: " + id));
        return mapToDTO(orderItems);
    }

    public OrderItemsResponseDTO updateOrderItem(Long id, OrderItemsDTO orderItemsDTO) {
         orderItemsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("OrderItem not found with id: " + id));
        OrderItems orderItems = mapToOrderItems(orderItemsDTO);
        orderItems.setId(id);
        return mapToDTO(orderItemsRepository.save(orderItems));
    }

    public void deleteOrderItemById(Long id) {
        if (!orderItemsRepository.existsById(id)) {
            throw new NotFoundException("OrderItem not found with id: " + id);
        }
        orderItemsRepository.deleteById(id);
    }

    public List<OrderItemsResponseDTO> getAllOrderItems(Integer page, Integer pageSize) {
        return orderItemsRepository.findAll(Pageable.ofSize(pageSize).withPage(page)).stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    private OrderItems mapToOrderItems(OrderItemsDTO orderItemsDTO) {
        OrderItems orderItems = new OrderItems();
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(orderItemsDTO.getCustomerOrderId());

        Product product = new Product();
        product.setId(orderItemsDTO.getProductId());

        orderItems.setProduct(product);
        orderItems.setCustomerOrder(customerOrder);
        orderItems.setPrice(orderItemsDTO.getPrice());
        orderItems.setQuantity(orderItemsDTO.getQuantity());

        return orderItems;
    }

    private OrderItemsResponseDTO mapToDTO(OrderItems orderItems) {
        OrderItemsResponseDTO orderItemsResponseDTO = new OrderItemsResponseDTO();
        orderItemsResponseDTO.setCustomerOrder(orderItems.getCustomerOrder());
        orderItemsResponseDTO.setQuantity(orderItems.getQuantity());
        orderItemsResponseDTO.setPrice(orderItems.getPrice());
        orderItemsResponseDTO.setProduct(orderItems.getProduct());
        return orderItemsResponseDTO;
    }

}
