package com.aminkbi.springbootInventory.repositories;

import com.aminkbi.springbootInventory.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
