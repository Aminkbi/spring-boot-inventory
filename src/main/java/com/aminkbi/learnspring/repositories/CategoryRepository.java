package com.aminkbi.learnspring.repositories;

import com.aminkbi.learnspring.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category getCategoryById(Long id);

}
