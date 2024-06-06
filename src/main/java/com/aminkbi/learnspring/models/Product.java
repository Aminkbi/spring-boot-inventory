package com.aminkbi.learnspring.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name should be provided")
    private String name;

    private String description;

    private Date expiryDate;

    @Column(nullable = false)
    @NotNull(message = "Description should be provided")
    private Double price;

    @NotNull(message = "Quantity should be provided")
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
