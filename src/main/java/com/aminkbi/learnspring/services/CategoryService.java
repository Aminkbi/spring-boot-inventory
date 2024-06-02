package com.aminkbi.learnspring.services;


import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.aminkbi.learnspring.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id){
        return categoryRepository.getCategoryById(id);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = categoryRepository.getCategoryById(id);
        if (existingCategory != null) {
            existingCategory.setName(updatedCategory.getName());
            return categoryRepository.save(existingCategory);

        } else {
            throw new NotFoundException("Category not found with id: " + id);
        }
    }

    public void deleteCategoryById(Long id){
        categoryRepository.deleteById(id);
    }

    public Page<Category> getAllCategories(Integer page, Integer pageSize){
        return categoryRepository.findAll(Pageable.ofSize(pageSize).withPage(page));
    }
}
