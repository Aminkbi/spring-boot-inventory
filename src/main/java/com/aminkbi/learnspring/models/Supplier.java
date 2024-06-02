package com.aminkbi.learnspring.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Name should be provided")
    private String name;

    private String contactName;
    private String contactEmail;
    private String contactPhone;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> products;

}