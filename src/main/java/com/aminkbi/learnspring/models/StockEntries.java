package com.aminkbi.learnspring.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


//CREATE TABLE StockEntries (
//        id BIGINT AUTO_INCREMENT PRIMARY KEY,
//        product_id BIGINT,
//        change_quantity INT NOT NULL,
//        change_type VARCHAR(50) NOT NULL,  -- e.g., 'addition' or 'removal'
//change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//FOREIGN KEY (product_id) REFERENCES Products(id)
//        );
@Data
@Entity
public class StockEntries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;

    @Column(nullable = false)
    private int changeQuantity;

    @Column(nullable = false)
    private String changeType; // e.g., 'addition' or 'removal'

    @Column(nullable = false)
    private LocalDateTime changeDate = LocalDateTime.now();
}
