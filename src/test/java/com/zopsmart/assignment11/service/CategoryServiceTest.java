package com.zopsmart.assignment11.service;

import com.zopsmart.assignment11.dao.CategoryDao;
import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryDao categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {

        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category("Grocery"));
        expectedCategories.add(new Category("Electronics"));
        expectedCategories.add(new Category("Snacks"));
        when(categoryRepository.findAll()).thenReturn(expectedCategories);


        List<Category> actualCategories = categoryService.getAllCategories();


        assertEquals(expectedCategories.size(), actualCategories.size());
        assertEquals(expectedCategories.get(0).getName(), actualCategories.get(0).getName());
        assertEquals(expectedCategories.get(1).getName(), actualCategories.get(1).getName());
        assertEquals(expectedCategories.get(2).getName(), actualCategories.get(2).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById() throws ResourceNotFoundException {
        Long categoryId = 1L;
        Category expectedCategory = new Category("Electronics");
        expectedCategory.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        Category actualCategory = categoryService.getCategoryById(categoryId);

        assertEquals(expectedCategory.getName(), actualCategory.getName());
        assertEquals(expectedCategory.getId(), actualCategory.getId());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetCategoryById_NotFound() {

        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testAddCategory() {
        Category category = new Category("Electronics");
        when(categoryRepository.findByName(category.getName())).thenReturn(new ArrayList<>());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category savedCategory = categoryService.addCategory(category);

        assertEquals(category.getName(), savedCategory.getName());
        verify(categoryRepository, times(1)).findByName(category.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testAddCategory_ExistingCategory() {
        Category existingCategory = new Category("Electronics");
        existingCategory.setId(1L);
        when(categoryRepository.findByName(existingCategory.getName())).thenReturn(List.of(existingCategory));

        Category savedCategory = categoryService.addCategory(existingCategory);
        assertEquals(existingCategory.getId(), savedCategory.getId());
        assertEquals(existingCategory.getName(), savedCategory.getName());
        verify(categoryRepository, never()).save(existingCategory);
    }

    @Test
    public void testUpdateCategory_Success() throws ResourceNotFoundException {
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        Category updateCategory = new Category();
        updateCategory.setName("Updated Category");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category updatedCategory = categoryService.updateCategory(1L, updateCategory);

        assertEquals(updateCategory.getName(), updatedCategory.getName());
    }

    @Test
    public void givenNonexistentCategoryId_whenUpdatingCategory_thenThrowsResourceNotFoundException() {
        Category updatedCategory = new Category();
        updatedCategory.setName("Magazines");

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(-1L, updatedCategory);
        }, "Expected ResourceNotFoundException to be thrown");
    }
}
