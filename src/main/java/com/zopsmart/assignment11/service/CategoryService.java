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
            throw new ResourceNotFoundException("Id not found"+categoryId);
        }
        return category.get();
    }
    public Category createCategory(Category category) {
        Long categoryId = category.getId();
        if (!doesCategoryExists(categoryId)) {
            return categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category already exists: " + categoryId);
        }
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) throws ResourceNotFoundException {
        Category category = getCategoryById(categoryId);
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }


    public boolean doesCategoryExists(Long categoryId){
        return categoryRepository.existsById(categoryId);
    }
}
