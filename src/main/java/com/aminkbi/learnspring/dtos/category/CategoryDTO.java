package com.aminkbi.learnspring.dtos.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class CategoryDTO {

    @NotEmpty
    private String name;

}
