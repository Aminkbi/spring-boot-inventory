package com.aminkbi.learnspring.dtos.category;

import com.aminkbi.learnspring.models.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDTO {

    @NotEmpty
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Product> products;

}
