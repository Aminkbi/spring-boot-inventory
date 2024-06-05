package com.aminkbi.learnspring.controllers;


import com.aminkbi.learnspring.dtos.orderItem.OrderItemsDTO;
import com.aminkbi.learnspring.dtos.orderItem.OrderItemsResponseDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.response.DeleteResponse;
import com.aminkbi.learnspring.models.response.ResponseModel;
import com.aminkbi.learnspring.services.OrderItemsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order-items")
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    public OrderItemsController(OrderItemsService orderItemsService){
        this.orderItemsService = orderItemsService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<OrderItemsResponseDTO>> addOrderItems(@RequestBody @Valid OrderItemsDTO orderItemsDTO){

        OrderItemsResponseDTO addedOrderItems = orderItemsService.addOrderItem(orderItemsDTO);

        var responseModel = new ResponseModel<>(1, "OrderItems Created Successfully",addedOrderItems);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<OrderItemsResponseDTO>> getOrderItems(@PathVariable Long id) throws NotFoundException {
        OrderItemsResponseDTO orderItems = orderItemsService.getOrderItemById(id);

        var responseModel = new ResponseModel<>(1, "Fetched OrderItems Successfully",orderItems);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<OrderItemsResponseDTO>> updateOrderItems(@PathVariable Long id, @RequestBody OrderItemsDTO orderItemsDTO) {
        OrderItemsResponseDTO updatedOrderItems = orderItemsService.updateOrderItem(id, orderItemsDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(1, "OrderItems Updated Successfully", updatedOrderItems));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteOrderItems(@PathVariable Long id) {
        orderItemsService.deleteOrderItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "OrderItems Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<OrderItemsResponseDTO>>> listOrderItems(@RequestParam @NotNull @Min(value = 0,message = "minimum value should be 0") Integer page,
                                                                                 @RequestParam @NotNull @Min(value=1,message = "minimum value should be 0") @Max(value=20, message = "value should not exceed 20") Integer pageSize) {
        return ResponseEntity.ok(new ResponseModel<>(1,"OrderItems fetched successfully",orderItemsService.getAllOrderItems(page, pageSize)));
    }

}
