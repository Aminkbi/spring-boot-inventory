package com.aminkbi.learnspring.dtos.stockEntries;

import com.aminkbi.learnspring.constants.stockEntries.ChangeTypes;
import com.aminkbi.learnspring.models.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockEntriesResponseDTO {

    private Long id;


    private Product product;

    private int changeQuantity;

    private ChangeTypes changeType; // e.g., 'addition' or 'removal'

    private LocalDateTime changeDate;
}
