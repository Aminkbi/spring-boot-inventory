package com.aminkbi.learnspring.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


//CREATE TABLE OrderItems (
//        id BIGINT AUTO_INCREMENT PRIMARY KEY,
//        order_id BIGINT,
//        product_id BIGINT,
//        quantity INT NOT NULL,
//        price DECIMAL(10, 2) NOT NULL,
//FOREIGN KEY (order_id) REFERENCES Orders(id),
//FOREIGN KEY (product_id) REFERENCES Products(id)
//        );
@Data
@Entity
public class OrderItems {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;

    @NotNull(message = "Quantity should be provided")
    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    @NotNull(message = "Description should be provided")
    private Double price;
}
