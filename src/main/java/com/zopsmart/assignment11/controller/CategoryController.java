package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import com.zopsmart.assignment11.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Api(value = "Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @ApiOperation(value = "View a list of available categories", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Categories"),
            @ApiResponse(code = 404, message = "No category found"),
            @ApiResponse(code = 500, message = "Internal Server Error")

    })
    @GetMapping("/get")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            HttpHeaders headers = new HttpHeaders();
            if (categories == null || categories.isEmpty()) {
                headers.add("Custom-Header", "Category Not Found");
                return ResponseEntity.noContent().headers(headers).build();
            }
            headers.add("Custom_Header", "Category Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(categories);
        } catch (Exception e) {
            LOGGER.error("Error getting all categories: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Category created successfully"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/post")
    public ResponseEntity<Object> postCategory(@RequestBody Category category) {
        HttpHeaders headers = new HttpHeaders();
        try {
            Category addedCategory = categoryService.addCategory(category);
            headers.add("Custom-Header", "Category added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(addedCategory);
        } catch (IllegalArgumentException e) {
            headers.add("Custom-Header", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(e.getMessage());
        }
    }


    @ApiOperation(value = "Get a category by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved category"),
            @ApiResponse(code = 404, message = "Category not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/get/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            HttpHeaders httpHeaders = new HttpHeaders();
            if (category == null) {
                httpHeaders.add("Custom-Header", "Category Not Found");
                return ResponseEntity.notFound().headers(httpHeaders).build();
            }
            httpHeaders.add("Custom-Header", "Category Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(category);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOGGER.error("Error getting category by ID: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Update a category by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category updated successfully"),
            @ApiResponse(code = 404, message = "Category not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/put/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            HttpHeaders headers = new HttpHeaders();
            if (updatedCategory == null) {
                headers.add("Custom-Header", "Category Not Found");
                return ResponseEntity.notFound().headers(headers).build();
            }
            headers.add("Custom-Header", "Category Updated Successfully");
            return ResponseEntity.ok().body(updatedCategory);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOGGER.error("Error updating category: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
