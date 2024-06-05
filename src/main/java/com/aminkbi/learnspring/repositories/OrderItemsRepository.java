package com.aminkbi.learnspring.repositories;

import com.aminkbi.learnspring.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {


}