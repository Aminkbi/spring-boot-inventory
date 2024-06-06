package com.aminkbi.springbootInventory.repositories;

import com.aminkbi.springbootInventory.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {


}
