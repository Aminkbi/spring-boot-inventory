package com.aminkbi.learnspring.services;

import com.aminkbi.learnspring.dtos.category.CategoryDTO;
import com.aminkbi.learnspring.dtos.category.CategoryResponseDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Category;
import com.aminkbi.learnspring.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");

        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO result = categoryService.addCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Category", result.getName());
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponseDTO result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Category", result.getName());
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.getCategoryById(1L));

        assertEquals("Category not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateCategory() {
        // Arrange
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Old Category");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Updated Category");

        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // Act
        CategoryResponseDTO result = categoryService.updateCategory(1L, categoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Category", result.getName());
        assertEquals(1L, result.getId());
    }


    @Test
    void testDeleteCategoryById() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.deleteCategoryById(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCategoryById_NotFound() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> categoryService.deleteCategoryById(1L));

        assertEquals("Category not found with id: 1", exception.getMessage());
    }

    @Test
    void testGetAllCategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> page = new PageImpl<>(Arrays.asList(category1, category2));

        when(categoryRepository.findAll(pageable)).thenReturn(page);

        List<CategoryResponseDTO> result = categoryService.getAllCategories(pageable);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("Category 2", result.get(1).getName());
    }
}
