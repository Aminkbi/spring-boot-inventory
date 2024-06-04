package com.aminkbi.learnspring.dtos.customerOrder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CustomerOrderDTO {


    @NotNull
    private Float total;

    @Size(max = 50)
    @NotNull
    private String status;

    @NotNull
    private Long userId;
}
