package com.aminkbi.springbootInventory.services;


import com.aminkbi.springbootInventory.dtos.customerOrder.CustomerOrderDTO;
import com.aminkbi.springbootInventory.dtos.customerOrder.CustomerOrderResponseDTO;
import com.aminkbi.springbootInventory.exceptions.NotFoundException;
import com.aminkbi.springbootInventory.models.AppUser;
import com.aminkbi.springbootInventory.models.CustomerOrder;
import com.aminkbi.springbootInventory.repositories.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    @Autowired
    public CustomerOrderService(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    public CustomerOrderResponseDTO addCustomerOrder(CustomerOrderDTO customerOrderDTO) {
        CustomerOrder customerOrder = customerOrderRepository.save(this.mapToCustomerOrder(customerOrderDTO));
        return mapToDTO(customerOrder);
    }

    public CustomerOrderResponseDTO getCustomerOrderById(Long id) {
        CustomerOrder customerOrder = customerOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CustomerOrder not found with id: " + id));
        return mapToDTO(customerOrder);
    }

    public CustomerOrderResponseDTO updateCustomerOrder(Long id, CustomerOrderDTO customerOrderDTO) {
         customerOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CustomerOrder not found with id: " + id));
        CustomerOrder customerOrder = mapToCustomerOrder(customerOrderDTO);
        customerOrder.setId(id);
        return mapToDTO(customerOrderRepository.save(customerOrder));
    }

    public void deleteCustomerOrderById(Long id) {
        if (!customerOrderRepository.existsById(id)) {
            throw new NotFoundException("CustomOrder not found with id: " + id);
        }
        customerOrderRepository.deleteById(id);
    }

    public List<CustomerOrderResponseDTO> getAllCustomOrders(Integer page, Integer pageSize) {
        return customerOrderRepository.findAll(Pageable.ofSize(pageSize).withPage(page)).stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    private CustomerOrder mapToCustomerOrder(CustomerOrderDTO customerOrderDTO) {
        CustomerOrder customerOrder = new CustomerOrder();
        AppUser appUser = new AppUser();
        appUser.setId(customerOrder.getId());
        customerOrder.setUser(appUser);
        customerOrder.setTotal(customerOrderDTO.getTotal());
        customerOrder.setStatus(customerOrderDTO.getStatus());
        return customerOrder;
    }

    private CustomerOrderResponseDTO mapToDTO(CustomerOrder customerOrder) {
        CustomerOrderResponseDTO customerOrderResponseDTO = new CustomerOrderResponseDTO();
        customerOrderResponseDTO.setId(customerOrder.getId());
        customerOrderResponseDTO.setOrderDate(customerOrder.getOrderDate());
        customerOrderResponseDTO.setUser(customerOrder.getUser());
        customerOrderResponseDTO.setStatus(customerOrder.getStatus());
        customerOrderResponseDTO.setTotal(customerOrder.getTotal());
        customerOrderResponseDTO.setOrderItems(customerOrder.getOrderItems());
        return customerOrderResponseDTO;
    }

}
