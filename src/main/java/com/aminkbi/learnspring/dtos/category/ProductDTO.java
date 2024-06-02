package com.aminkbi.learnspring.dtos.category;



import com.aminkbi.learnspring.models.Category;
import com.aminkbi.learnspring.models.Supplier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name should be provided")
    private String name;

    private String description;

    @NotNull(message = "Description should be provided")
    private Double price;

    @NotNull(message = "Quantity should be provided")
    private Integer quantity;

    @NotNull(message = "categoryId should be provided")
    private Long categoryId;

    @NotNull(message = "supplierId should be provided")
    private Long supplierId;


    }