package com.zopsmart.assignment11.service;

import com.zopsmart.assignment11.dao.CategoryDao;
import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) throws ResourceNotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Id not found" + categoryId);
        }
        return category.get();
    }

    public Category addCategory(Category category) {
        String categoryName = category.getName();
        if (categoryName == null) {
            throw new IllegalArgumentException("Category name cannot be null");
        }
        List<Category> existingCategories = categoryRepository.findByName(categoryName);
        if (!existingCategories.isEmpty()) {
            category.setId(existingCategories.get(0).getId());
        } else {
            category = categoryRepository.save(category);
        }
        return category;
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) throws ResourceNotFoundException {
        Category category = getCategoryById(categoryId);
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }

}
