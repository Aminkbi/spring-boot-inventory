package com.aminkbi.learnspring.repositories;

import com.aminkbi.learnspring.models.StockEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockEntriesRepository extends JpaRepository<StockEntries, Long> {

}
