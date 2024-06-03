package com.aminkbi.learnspring.dtos.customerOrder;

import com.aminkbi.learnspring.models.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;


@Data
public class CustomerOrderDTO {

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @NotNull
    private Float total;

    @Size(max = 50)
    private String status;


    private AppUser user;
}
