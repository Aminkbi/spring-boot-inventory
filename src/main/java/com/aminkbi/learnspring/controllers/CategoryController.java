package com.aminkbi.learnspring.controllers;


import com.aminkbi.learnspring.dtos.category.CategoryDTO;
import com.aminkbi.learnspring.dtos.category.CategoryResponseDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Category;
import com.aminkbi.learnspring.models.response.DeleteResponse;
import com.aminkbi.learnspring.models.response.ResponseModel;
import com.aminkbi.learnspring.models.response.UpdateResponse;
import com.aminkbi.learnspring.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<CategoryResponseDTO>> addCategory(@Valid @RequestBody CategoryDTO categoryDTO){

        Category addedCategory = categoryService.addCategory(categoryDTO);

        var responseCategory = new CategoryResponseDTO();
        responseCategory.setName(addedCategory.getName());

        var responseModel = new ResponseModel<>(1, "Category Created Successfully",responseCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<CategoryResponseDTO>> getCategory(@PathVariable Long id) throws NotFoundException {
        var category = categoryService.getCategoryById(id);

        if(category == null){
            throw new NotFoundException("Category not found");
        }
        var dto = new CategoryResponseDTO();
        dto.setName(category.getName());
        var responseModel = new ResponseModel<>(1, "Fetched Category Successfully",dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO category) throws NotFoundException {
        Category updatedCategory = categoryService.updateCategory(id, category);
        if(updatedCategory == null){
            throw new NotFoundException("Category not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateResponse(1, "Category Updated Successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCategory(@PathVariable Long id) throws NotFoundException {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "Category Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<CategoryResponseDTO>>> listCategories(@RequestParam @NotNull Integer page, @RequestParam @NotNull Integer pageSize) {

        var fetchedCategories = categoryService.getAllCategories(page, pageSize);

        var responseCategories = fetchedCategories.get().map(category -> {
            var responseCategory = new CategoryResponseDTO();
            responseCategory.setName(category.getName());
            return responseCategory;
        }).toList();

        return ResponseEntity.ok(new ResponseModel<>(1,"Categories fetched successfully",responseCategories));
    }
}
