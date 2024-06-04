package com.aminkbi.learnspring.controllers;

import com.aminkbi.learnspring.dtos.category.CategoryDTO;
import com.aminkbi.learnspring.dtos.category.CategoryResponseDTO;
import com.aminkbi.learnspring.models.response.DeleteResponse;
import com.aminkbi.learnspring.models.response.ResponseModel;
import com.aminkbi.learnspring.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ResponseModel<CategoryResponseDTO>> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.addCategory(categoryDTO);
        ResponseModel<CategoryResponseDTO> responseModel = new ResponseModel<>(1, "Category Created Successfully", categoryResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<CategoryResponseDTO>> getCategory(@PathVariable Long id) {
        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(id);
        ResponseModel<CategoryResponseDTO> responseModel = new ResponseModel<>(1, "Fetched Category Successfully", categoryResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<CategoryResponseDTO>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        var responseDTO = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel<>(1, "Category Updated Successfully", responseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse(1, "Category Deleted Successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<CategoryResponseDTO>>> listCategories(@RequestParam @NotNull @Min(value = 0,message = "minimum value should be 0") Integer page,
                                                                                   @RequestParam @NotNull @Min(value=1,message = "minimum value should be 0") @Max(value=20, message = "value should not exceed 20") Integer pageSize) {
        Pageable pageable = PageRequest.ofSize(pageSize).withPage(page);
        var fetchedCategories = categoryService.getAllCategories(pageable);
        ResponseModel<List<CategoryResponseDTO>> responseModel = new ResponseModel<>(1, "Categories fetched successfully", fetchedCategories);
        return ResponseEntity.ok(responseModel);
    }


}
