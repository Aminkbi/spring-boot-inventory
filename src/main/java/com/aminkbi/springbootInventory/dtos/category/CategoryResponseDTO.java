package com.aminkbi.springbootInventory.dtos.category;

import com.aminkbi.springbootInventory.models.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDTO {

    private Long id;

    @NotEmpty
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Product> products;

}
