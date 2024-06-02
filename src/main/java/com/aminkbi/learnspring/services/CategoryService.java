package com.aminkbi.learnspring.services;


import com.aminkbi.learnspring.dtos.category.CategoryDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.aminkbi.learnspring.repositories.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(CategoryDTO categoryDTO){

        var category = new Category();
        category.setName(categoryDTO.getName());

        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id){
        return categoryRepository.getCategoryById(id);
    }

    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.getCategoryById(id);
        if (existingCategory != null) {
            existingCategory.setName(categoryDTO.getName());
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
