package com.zopsmart.assignment11.service;

import com.zopsmart.assignment11.dao.CategoryDao;
import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.exception.BadRequestException;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryRepository;

    /**
     * getAllCategories function to retrieve all categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * getCategoryById function to retrieve category based on id provided
     */

    public Category getCategoryById(Long categoryId) throws ResourceNotFoundException, BadRequestException {

        if (categoryId <= 0) {
            throw new BadRequestException("Invalid Id provided");
        }
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found with corresponding id:" + categoryId);
        }
        return category.get();
    }


    /**
     * addCategory function to create new category
     */
    public Category addCategory(Category category) throws BadRequestException {
        String categoryName = category.getName();
        if (categoryName == null || categoryName.equals("")) {
            throw new BadRequestException("Category name cannot be null or empty");
        }
        List<Category> existingCategories = categoryRepository.findByName(categoryName);
        if (!existingCategories.isEmpty()) {
            category.setId(existingCategories.get(0).getId());
        } else {
            category = categoryRepository.save(category);
        }
        return category;
    }


    /**
     * updateCategory function to update a category based on id
     */
    public Category updateCategory(Long categoryId, Category categoryDetails) throws ResourceNotFoundException, BadRequestException {
        Category category = getCategoryById(categoryId);
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }

}
