package com.aminkbi.springbootInventory.dtos.supplier;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class SupplierDTO {

    @NotEmpty(message = "Name should be provided")
    private String name;

    private String contactName;
    private String contactEmail;
    private String contactPhone;

}

