package com.aminkbi.springbootInventory.services;


import com.aminkbi.springbootInventory.dtos.supplier.SupplierDTO;
import com.aminkbi.springbootInventory.dtos.supplier.SupplierResponseDTO;
import com.aminkbi.springbootInventory.exceptions.NotFoundException;
import com.aminkbi.springbootInventory.models.Supplier;
import com.aminkbi.springbootInventory.repositories.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;


    @Autowired
    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    public SupplierResponseDTO addSupplier(SupplierDTO supplierDTO){
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setContactPhone(supplierDTO.getContactPhone());
        supplier.setContactEmail(supplierDTO.getContactEmail());
        supplier.setContactName(supplierDTO.getContactName());
        Supplier savedSupplier = supplierRepository.save(supplier);

        return mapToDTO(savedSupplier);
    }

    public SupplierResponseDTO getSupplierById(Long id){
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id " + id));

        return this.mapToDTO(supplier);
    }

    public SupplierResponseDTO updateSupplier(Long id, @Valid SupplierDTO supplierDTO) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id " + id));

        existingSupplier.setName(supplierDTO.getName());
        existingSupplier.setContactEmail(supplierDTO.getContactEmail());
        existingSupplier.setContactName(supplierDTO.getContactName());
        existingSupplier.setContactPhone(supplierDTO.getContactPhone());

        return this.mapToDTO(supplierRepository.save(existingSupplier));
    }

    public void deleteSupplierById(Long id){
        supplierRepository.deleteById(id);
    }

    public List<SupplierResponseDTO> getAllSuppliers(Integer page, Integer pageSize){
        return supplierRepository.findAll(Pageable.ofSize(pageSize).withPage(page))
                .stream().map(this::mapToDTO).toList();
    }

    private SupplierResponseDTO mapToDTO(Supplier supplier) {
        SupplierResponseDTO supplierResponseDTO = new SupplierResponseDTO();
        supplierResponseDTO.setId(supplier.getId());
        supplierResponseDTO.setName(supplier.getName());
        supplierResponseDTO.setContactEmail(supplier.getContactEmail());
        supplierResponseDTO.setContactPhone(supplier.getContactPhone());
        supplierResponseDTO.setContactName(supplier.getContactName());
        return supplierResponseDTO;
    }
}
