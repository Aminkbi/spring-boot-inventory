package com.aminkbi.learnspring.controllers;


import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Supplier;
import com.aminkbi.learnspring.models.response.DeleteResponse;
import com.aminkbi.learnspring.models.response.ResponseModel;
import com.aminkbi.learnspring.models.response.UpdateResponse;
import com.aminkbi.learnspring.services.SupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<Supplier>> addSupplier(@Valid @RequestBody Supplier supplier){
        Supplier toBeAddedSupplier = new Supplier();
        toBeAddedSupplier.setName(supplier.getName());

        Supplier addedSupplier = supplierService.addSupplier(toBeAddedSupplier);
        ResponseModel<Supplier> responseModel = new ResponseModel<>(1, "Supplier Created Successfully",addedSupplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<Supplier>> getSupplier(@PathVariable Long id) throws NotFoundException {
        Optional<Supplier> supplier = supplierService.getSupplierById(id);
        if(supplier.isEmpty()){
            throw new NotFoundException("Supplier not found");
        }
        ResponseModel<Supplier> responseModel = new ResponseModel<>(1, "Fetched Supplier Successfully",supplier.get());
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateResponse> updateSupplier(@PathVariable Long id,@Valid @RequestBody Supplier supplier) throws NotFoundException {
        Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);
        if(updatedSupplier == null){
            throw new NotFoundException("Supplier not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateResponse(1, "Supplier Updated Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteSupplier(@PathVariable Long id) throws NotFoundException {
        supplierService.deleteSupplierById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "Supplier Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<Supplier>>> listCategories(@RequestParam @NotNull Integer page, @RequestParam @NotNull Integer pageSize) {
        return ResponseEntity.ok(new ResponseModel<>(1,"Categories fetched successfully",supplierService.getAllCategories(page, pageSize).get().toList()));
    }
}
