package com.aminkbi.springbootInventory.controllers;


import com.aminkbi.springbootInventory.dtos.stockEntries.StockEntriesResponseDTO;
import com.aminkbi.springbootInventory.dtos.stockEntries.StockEntriesDTO;
import com.aminkbi.springbootInventory.exceptions.NotFoundException;
import com.aminkbi.springbootInventory.models.response.DeleteResponse;
import com.aminkbi.springbootInventory.models.response.ResponseModel;
import com.aminkbi.springbootInventory.services.StockEntriesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/stock-entries")
public class StockEntriesController {

    private final StockEntriesService stockEntriesService;

    public StockEntriesController(StockEntriesService stockEntriesService){
        this.stockEntriesService = stockEntriesService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<StockEntriesResponseDTO>> addStockEntries(@RequestBody @Valid StockEntriesDTO stockEntriesDTO){

        StockEntriesResponseDTO addedStockEntries = stockEntriesService.addStockEntries(stockEntriesDTO);

        var responseModel = new ResponseModel<>(1, "StockEntries Created Successfully",addedStockEntries);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<StockEntriesResponseDTO>> getStockEntries(@PathVariable Long id) throws NotFoundException {
        StockEntriesResponseDTO stockEntries = stockEntriesService.getStockEntriesById(id);

        var responseModel = new ResponseModel<>(1, "Fetched StockEntries Successfully",stockEntries);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<StockEntriesResponseDTO>> updateStockEntries(@PathVariable Long id, @RequestBody StockEntriesDTO stockEntriesDTO) {
        StockEntriesResponseDTO updatedStockEntries = stockEntriesService.updateStockEntries(id, stockEntriesDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(1, "StockEntries Updated Successfully", updatedStockEntries));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteStockEntries(@PathVariable Long id) {
        stockEntriesService.deleteStockEntriesById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "StockEntries Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<StockEntriesResponseDTO>>> listStockEntries(@RequestParam @NotNull @Min(value = 0,message = "minimum value should be 0") Integer page,
                                                                                 @RequestParam @NotNull @Min(value=1,message = "minimum value should be 0") @Max(value=20, message = "value should not exceed 20") Integer pageSize) {
        return ResponseEntity.ok(new ResponseModel<>(1,"StockEntries fetched successfully",stockEntriesService.getAllStockEntries(page, pageSize)));
    }

}
