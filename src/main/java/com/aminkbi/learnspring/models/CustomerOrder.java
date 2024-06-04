package com.aminkbi.learnspring.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Date;
import java.util.List;


//id BIGINT AUTO_INCREMENT PRIMARY KEY,
//order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//user_id BIGINT,
//total DECIMAL(10, 2) NOT NULL,
//status VARCHAR(50) NOT NULL,
//FOREIGN KEY (user_id) REFERENCES Users(id)
@Entity
@Data
public class CustomerOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(insertable = false, updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(nullable = false)
    private Float total;

    @Column(nullable = false)
    @Size(max = 50)
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    private AppUser user;

    @OneToMany(mappedBy = "orderItems", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItems> orderItems;

}
