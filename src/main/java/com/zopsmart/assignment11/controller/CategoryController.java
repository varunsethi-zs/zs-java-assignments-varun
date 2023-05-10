package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.exception.BadRequestException;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import com.zopsmart.assignment11.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryController class to call service layer functions of category
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);


    /**
     * getAllCategories function to retrieve all categories
     */

    @Operation(summary = "View a list of available categories", tags = "Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Categories"),
            @ApiResponse(responseCode = "404", description = "No category found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    @GetMapping("/get")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        HttpHeaders headers = new HttpHeaders();
        if (categories == null || categories.isEmpty()) {
            headers.add("Custom-Header", "Category Not Found");
            return ResponseEntity.noContent().headers(headers).build();
        }
        headers.add("Custom_Header", "Category Found Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(categories);
    }


    /**
     * postCategory function to create new category
     */
    @Operation(summary = "Add a new category", tags = "Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters Passed")
    })
    @PostMapping("/post")
    public ResponseEntity<Object> postCategory(@RequestBody Category category) throws BadRequestException {
        HttpHeaders headers = new HttpHeaders();
        Category addedCategory = categoryService.addCategory(category);
        headers.add("Custom-Header", "Category added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(addedCategory);
    }

    /**
     * getCategoryById function to get category based on given id
     */

    @Operation(summary = "Get a category by its id", tags = "Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category with supplied id"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid Id passed")
    })
    @GetMapping("/get/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) throws BadRequestException, ResourceNotFoundException {
        Category category = categoryService.getCategoryById(categoryId);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (category == null) {
            httpHeaders.add("Custom-Header", "Category Not Found");
            return ResponseEntity.notFound().headers(httpHeaders).build();
        }
        httpHeaders.add("Custom-Header", "Category Found Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(category);
    }

    /**
     * updateCategory function to update a category with given id
     */

    @Operation(summary = "Update a category by ID", tags = "Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully with supplied id"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters Passed")
    })
    @PutMapping("/put/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) throws BadRequestException, ResourceNotFoundException {

        Category updatedCategory = categoryService.updateCategory(categoryId, category);
        HttpHeaders headers = new HttpHeaders();
        if (updatedCategory == null) {
            headers.add("Custom-Header", "Category Not Found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom-Header", "Category Updated Successfully");
        return ResponseEntity.ok().headers(headers).body(updatedCategory);
    }
}
