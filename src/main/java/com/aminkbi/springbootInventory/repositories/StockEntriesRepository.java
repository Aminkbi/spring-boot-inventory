package com.aminkbi.springbootInventory.repositories;

import com.aminkbi.springbootInventory.models.StockEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockEntriesRepository extends JpaRepository<StockEntries, Long> {

}
