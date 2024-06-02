package com.aminkbi.learnspring.services;


import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Supplier;
import com.aminkbi.learnspring.repositories.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;


    @Autowired
    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    public Supplier addSupplier(@Valid Supplier supplier){
        return supplierRepository.save(supplier);
    }

    public Optional<Supplier> getSupplierById(Long id){
        return supplierRepository.findById(id);
    }

    public Supplier updateSupplier(Long id, @Valid Supplier updatedSupplier) {
        Optional<Supplier> existingSupplier = supplierRepository.findById(id);
        if (existingSupplier.isPresent()) {
            Supplier supplier = existingSupplier.get();
            supplier.setName(updatedSupplier.getName());
            if(updatedSupplier.getContactEmail() != null){
                supplier.setContactEmail(updatedSupplier.getContactEmail());
            }
            if(updatedSupplier.getContactName() != null){
                supplier.setContactName(updatedSupplier.getContactName());
            }
            if(updatedSupplier.getContactPhone() != null){
                supplier.setContactPhone(updatedSupplier.getContactPhone());
            }
            return supplierRepository.save(existingSupplier.get());

        } else {
            throw new NotFoundException("Supplier not found with id: " + id);
        }
    }

    public void deleteSupplierById(Long id){
        supplierRepository.deleteById(id);
    }

    public Page<Supplier> getAllCategories(Integer page, Integer pageSize){
        return supplierRepository.findAll(Pageable.ofSize(pageSize).withPage(page));
    }
}
