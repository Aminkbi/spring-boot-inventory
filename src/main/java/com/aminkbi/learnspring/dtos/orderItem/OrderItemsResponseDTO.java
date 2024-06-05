package com.aminkbi.learnspring.dtos.orderItem;

import com.aminkbi.learnspring.models.CustomerOrder;
import com.aminkbi.learnspring.models.Product;
import lombok.Data;

@Data
public class OrderItemsResponseDTO {
    private Long id;

    private Product product;

    private CustomerOrder customerOrder;

    private Integer quantity;

    private Double price;
}
