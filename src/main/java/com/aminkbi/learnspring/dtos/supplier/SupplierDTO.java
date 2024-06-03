package com.aminkbi.learnspring.dtos.supplier;

import com.aminkbi.learnspring.models.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;


@Data
public class SupplierDTO {

    @NotEmpty(message = "Name should be provided")
    private String name;

    private String contactName;
    private String contactEmail;
    private String contactPhone;

}

