package com.aminkbi.springbootInventory.dtos.stockEntries;

import com.aminkbi.springbootInventory.constants.stockEntries.ChangeTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class StockEntriesDTO {

    @NotNull(message = "ProductId should be provided")
    private Long productId;

    @Column(nullable = false)
    @NotNull(message = "changeQuantity should be provided")
    private int changeQuantity;

    @Column(nullable = false)
    @NotNull(message = "changeType should be provided")
    private ChangeTypes changeType; // e.g., 'addition' or 'removal'

}
