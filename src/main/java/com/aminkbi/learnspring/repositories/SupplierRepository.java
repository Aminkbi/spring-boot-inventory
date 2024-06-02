package com.aminkbi.learnspring.repositories;

import com.aminkbi.learnspring.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {


}
