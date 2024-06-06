package com.aminkbi.springbootInventory.repositories;

import com.aminkbi.springbootInventory.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {


}
