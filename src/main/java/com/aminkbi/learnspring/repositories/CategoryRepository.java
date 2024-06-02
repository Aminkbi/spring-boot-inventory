package com.aminkbi.learnspring.repositories;

import com.aminkbi.learnspring.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category getCategoryById(Long id);

}