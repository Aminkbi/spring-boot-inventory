package com.aminkbi.learnspring.dtos.orderItem;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OrderItemsDTO {


    @NotNull(message = "productId should be provided")
    private Long productId;

    @NotNull(message = "customerOrderId should be provided")
    private Long customerOrderId;

    @NotNull(message = "Quantity should be provided")
    private Integer quantity;

    @NotNull(message = "Description should be provided")
    private Double price;
}
