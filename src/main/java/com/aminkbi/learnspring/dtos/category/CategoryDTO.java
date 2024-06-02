package com.aminkbi.learnspring.dtos.category;

import com.aminkbi.learnspring.models.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;


@Data
public class CategoryDTO {

    @NotEmpty
    private String name;

    private List<Product> products;

}
