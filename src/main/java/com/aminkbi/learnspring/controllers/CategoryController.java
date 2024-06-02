package com.aminkbi.learnspring.controllers;


import com.aminkbi.learnspring.dtos.category.CategoryDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.Category;
import com.aminkbi.learnspring.models.response.DeleteResponse;
import com.aminkbi.learnspring.models.response.ResponseModel;
import com.aminkbi.learnspring.models.response.UpdateResponse;
import com.aminkbi.learnspring.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<ResponseModel<Category>> addCategory(@Valid @RequestBody CategoryDTO category){
        Category toBeAddedCategory = new Category();
        toBeAddedCategory.setName(category.getName());

        Category addedCategory = categoryService.addCategory(toBeAddedCategory);
        ResponseModel<Category> responseModel = new ResponseModel<>(1, "Category Created Successfully",addedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<CategoryDTO>> getCategory(@PathVariable Long id) throws NotFoundException {
        Category category = categoryService.getCategoryById(id);

        if(category == null){
            throw new NotFoundException("Category not found");
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setName(category.getName());
        ResponseModel<CategoryDTO> responseModel = new ResponseModel<>(1, "Fetched Category Successfully",dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateResponse> updateCategory(@PathVariable Long id,@Valid @RequestBody Category category) throws NotFoundException {
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
    public ResponseEntity<ResponseModel<List<Category>>> listCategories(@RequestParam @NotNull Integer page, @RequestParam @NotNull Integer pageSize) {
        return ResponseEntity.ok(new ResponseModel<>(1,"Categories fetched successfully",categoryService.getAllCategories(page, pageSize).get().toList()));
    }
}
