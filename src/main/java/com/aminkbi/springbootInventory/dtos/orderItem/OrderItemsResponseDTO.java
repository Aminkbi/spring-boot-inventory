package com.aminkbi.springbootInventory.dtos.orderItem;

import com.aminkbi.springbootInventory.models.CustomerOrder;
import com.aminkbi.springbootInventory.models.Product;
import lombok.Data;

@Data
public class OrderItemsResponseDTO {
    private Long id;

    private Product product;

    private CustomerOrder customerOrder;

    private Integer quantity;

    private Double price;
}
