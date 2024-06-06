package com.aminkbi.springbootInventory.controllers;


import com.aminkbi.springbootInventory.dtos.product.ProductDTO;
import com.aminkbi.springbootInventory.exceptions.NotFoundException;
import com.aminkbi.springbootInventory.models.Product;
import com.aminkbi.springbootInventory.models.response.DeleteResponse;
import com.aminkbi.springbootInventory.models.response.ResponseModel;
import com.aminkbi.springbootInventory.models.response.UpdateResponse;
import com.aminkbi.springbootInventory.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<Product>> addProduct(@RequestBody @Valid ProductDTO productDTO){

        Product addedProduct = productService.addProduct(productDTO);

        ResponseModel<Product> responseModel = new ResponseModel<>(1, "Product Created Successfully",addedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<Product>> getProduct(@PathVariable Long id) throws NotFoundException {
        Optional<Product> product = productService.getProductById(id);
        if(product.isEmpty()){
            throw new NotFoundException("Product not found");
        }

        ResponseModel<Product> responseModel = new ResponseModel<>(1, "Fetched Product Successfully",product.get());
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws NotFoundException {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        if(updatedProduct == null){
            throw new NotFoundException("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateResponse(1, "Product Updated Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteProduct(@PathVariable Long id) throws NotFoundException {
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "Product Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<Product>>> listProducts(@RequestParam @NotNull @Min(value = 0,message = "minimum value should be 0") Integer page, @RequestParam @NotNull @Min(value=1,message = "minimum value should be 0") @Max(value=20, message = "value should not exceed 20") Integer pageSize
            , @RequestParam(required = false) String name ) {
        if(name != null && !name.isBlank()){
            return ResponseEntity.ok(new ResponseModel<>(1,"Products fetched successfully",productService.findAllByNameContaining(page, pageSize,name)));
        }
        return ResponseEntity.ok(new ResponseModel<>(1,"Products fetched successfully",productService.getAllProducts(page, pageSize)));
    }

}
