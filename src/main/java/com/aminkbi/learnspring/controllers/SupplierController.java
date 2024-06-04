package com.aminkbi.learnspring.controllers;


import com.aminkbi.learnspring.dtos.supplier.SupplierDTO;
import com.aminkbi.learnspring.dtos.supplier.SupplierResponseDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.response.DeleteResponse;
import com.aminkbi.learnspring.models.response.ResponseModel;
import com.aminkbi.learnspring.models.response.UpdateResponse;
import com.aminkbi.learnspring.services.SupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<SupplierResponseDTO>> addSupplier(@Valid @RequestBody SupplierDTO supplierDTO){


        SupplierResponseDTO addedSupplier = supplierService.addSupplier(supplierDTO);
        var responseModel = new ResponseModel<>(1, "Supplier Created Successfully",addedSupplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<SupplierResponseDTO>> getSupplier(@PathVariable Long id) throws NotFoundException {
        SupplierResponseDTO supplier = supplierService.getSupplierById(id);
        var responseModel = new ResponseModel<>(1, "Fetched Supplier Successfully",supplier);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateResponse> updateSupplier(@PathVariable Long id,@Valid @RequestBody SupplierDTO supplier) throws NotFoundException {
        supplierService.updateSupplier(id, supplier);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateResponse(1, "Supplier Updated Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteSupplier(@PathVariable Long id) throws NotFoundException {
        supplierService.deleteSupplierById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "Supplier Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<SupplierResponseDTO>>> listCategories(@RequestParam @NotNull @Min(value = 0,message = "minimum value should be 0") Integer page,
                                                                                   @RequestParam @NotNull @Min(value=1,message = "minimum value should be 0") @Max(value=20, message = "value should not exceed 20") Integer pageSize) {
        return ResponseEntity.ok(new ResponseModel<>(1,"Categories fetched successfully",supplierService.getAllSuppliers(page, pageSize)));
    }
}
