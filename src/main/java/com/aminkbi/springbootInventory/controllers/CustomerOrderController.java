package com.aminkbi.springbootInventory.controllers;


import com.aminkbi.springbootInventory.dtos.customerOrder.CustomerOrderDTO;
import com.aminkbi.springbootInventory.dtos.customerOrder.CustomerOrderResponseDTO;
import com.aminkbi.springbootInventory.exceptions.NotFoundException;
import com.aminkbi.springbootInventory.models.response.DeleteResponse;
import com.aminkbi.springbootInventory.models.response.ResponseModel;
import com.aminkbi.springbootInventory.services.CustomerOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer-orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService){
        this.customerOrderService = customerOrderService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<CustomerOrderResponseDTO>> addCustomerOrder(@RequestBody @Valid CustomerOrderDTO customerOrderDTO){

        CustomerOrderResponseDTO addedCustomerOrder = customerOrderService.addCustomerOrder(customerOrderDTO);

        var responseModel = new ResponseModel<>(1, "CustomerOrder Created Successfully",addedCustomerOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<CustomerOrderResponseDTO>> getCustomerOrder(@PathVariable Long id) throws NotFoundException {
        CustomerOrderResponseDTO customerOrder = customerOrderService.getCustomerOrderById(id);

        var responseModel = new ResponseModel<>(1, "Fetched CustomerOrder Successfully",customerOrder);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<CustomerOrderResponseDTO>> updateCustomerOrder(@PathVariable Long id, @RequestBody CustomerOrderDTO customerOrderDTO) {
        CustomerOrderResponseDTO updatedCustomerOrder = customerOrderService.updateCustomerOrder(id, customerOrderDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(1, "CustomerOrder Updated Successfully", updatedCustomerOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCustomerOrder(@PathVariable Long id) {
        customerOrderService.deleteCustomerOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "CustomerOrder Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<CustomerOrderResponseDTO>>> listCustomerOrders(@RequestParam @NotNull @Min(value = 0,message = "minimum value should be 0") Integer page,
                                                                                 @RequestParam @NotNull @Min(value=1,message = "minimum value should be 0") @Max(value=20, message = "value should not exceed 20") Integer pageSize) {
        return ResponseEntity.ok(new ResponseModel<>(1,"CustomerOrders fetched successfully",customerOrderService.getAllCustomOrders(page, pageSize)));
    }

}
