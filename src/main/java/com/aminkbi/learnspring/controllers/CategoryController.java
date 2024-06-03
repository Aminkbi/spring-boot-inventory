package com.aminkbi.learnspring.controllers;

import com.aminkbi.learnspring.dtos.category.CategoryDTO;
import com.aminkbi.learnspring.dtos.category.CategoryResponseDTO;
import com.aminkbi.learnspring.models.Category;
import com.aminkbi.learnspring.models.response.DeleteResponse;
import com.aminkbi.learnspring.models.response.ResponseModel;
import com.aminkbi.learnspring.models.response.UpdateResponse;
import com.aminkbi.learnspring.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<CategoryResponseDTO>> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category addedCategory = categoryService.addCategory(categoryDTO);
        CategoryResponseDTO responseCategory = mapToDTO(addedCategory);
        ResponseModel<CategoryResponseDTO> responseModel = new ResponseModel<>(1, "Category Created Successfully", responseCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<CategoryResponseDTO>> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryResponseDTO responseCategory = mapToDTO(category);
        ResponseModel<CategoryResponseDTO> responseModel = new ResponseModel<>(1, "Fetched Category Successfully", responseCategory);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateResponse(1, "Category Updated Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "Category Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<CategoryResponseDTO>>> listCategories(@RequestParam @NotNull Integer page, @RequestParam @NotNull Integer pageSize) {
        Pageable pageable = PageRequest.ofSize(pageSize).withPage(page);
        var fetchedCategories = categoryService.getAllCategories(pageable);
        List<CategoryResponseDTO> responseCategories = fetchedCategories.stream().map(this::mapToDTO).collect(Collectors.toList());
        ResponseModel<List<CategoryResponseDTO>> responseModel = new ResponseModel<>(1, "Categories fetched successfully", responseCategories);
        return ResponseEntity.ok(responseModel);
    }

    private CategoryResponseDTO mapToDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setName(category.getName());
        dto.setId(category.getId());
        return dto;
    }
}
