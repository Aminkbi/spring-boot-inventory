package com.aminkbi.springbootInventory.repositories;

import com.aminkbi.springbootInventory.models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

}
