package com.aminkbi.learnspring.dtos.customerOrder;

import com.aminkbi.learnspring.models.AppUser;
import com.aminkbi.learnspring.models.OrderItems;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class CustomerOrderResponseDTO {

    private Long id;

    private Float total;

    private String status;

    private AppUser user;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<OrderItems> orderItems;

    private LocalDateTime orderDate;
}
