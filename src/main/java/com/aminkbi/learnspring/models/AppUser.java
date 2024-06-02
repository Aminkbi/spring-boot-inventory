package com.aminkbi.learnspring.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;


//CREATE TABLE Users (
//        id BIGINT AUTO_INCREMENT PRIMARY KEY,
//        username VARCHAR(50) NOT NULL UNIQUE,
//password VARCHAR(255) NOT NULL,
//email VARCHAR(100) NOT NULL UNIQUE,
//full_name VARCHAR(100),
//role_id BIGINT,
//FOREIGN KEY (role_id) REFERENCES Roles(id)
//        );

@Entity
@Data
public class AppUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 50)
    private String username;
}
