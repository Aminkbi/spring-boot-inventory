package com.aminkbi.springbootInventory.dtos.supplier;

import com.aminkbi.springbootInventory.models.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data

public class SupplierResponseDTO {

    private Long id;

    private String name;

    private String contactName;
    private String contactEmail;
    private String contactPhone;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Product> products;


}
