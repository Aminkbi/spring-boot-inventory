package com.aminkbi.springbootInventory.dtos.stockEntries;

import com.aminkbi.springbootInventory.constants.stockEntries.ChangeTypes;
import com.aminkbi.springbootInventory.models.Product;
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
