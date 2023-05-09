package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import com.zopsmart.assignment11.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.Matchers.*;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    ObjectMapper objectMapper;

    Category category1;
    Category category2;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        category1 = new Category(1L, "Category 1");
        category2 = new Category(2L, "Category 2");
    }

    @Test
    public void testGetAllCategories_Success() throws Exception {

        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/category/get"))
                .andExpect(status().isOk())
                .andExpect(header().string("Custom_Header", "Category Found Successfully"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Category 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Category 2")));
    }

    @Test
    public void testGetAllCategories_NotFound() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(null);
        mockMvc.perform(get("/category/get"))
                .andExpect(status().isNoContent());
        verify(categoryService, times(1)).getAllCategories();
    }


    @Test
    public void testPostCategory() throws Exception {

        Category category = new Category();
        category.setName("Books");

        Mockito.when(categoryService.addCategory(Mockito.any(Category.class)))
                .thenReturn(new Category(1L, "Books"));

        mockMvc.perform(MockMvcRequestBuilders.post("/category/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Custom-Header", "Category added successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Books"));
    }


    @Test
    public void testGetCategoryByIdSuccess() throws Exception {
        Category category = new Category(1L, "Electronics");

        when(categoryService.getCategoryById(1L)).thenReturn(category);

        mockMvc.perform(get("/category/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Electronics")))
                .andExpect(header().string("Custom-Header", "Category Found Successfully"));
    }

    @Test
    public void testGetCategoryByIdNotFound() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(null);

        mockMvc.perform(get("/category/get/1"))
                .andExpect(status().isNotFound())
                .andExpect(header().string("Custom-Header", "Category Not Found"));
    }

    @Test
    public void testGetCategoryByIdResourceNotFoundException() throws Exception {
        when(categoryService.getCategoryById(1L)).thenThrow(new ResourceNotFoundException("Category not found"));

        mockMvc.perform(get("/category/get/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testUpdateCategorySuccess() throws Exception {

        Category category = new Category();
        category.setId(1L);
        category.setName("TestCategory");

        String jsonRequest = "{\"name\": \"UpdatedTestCategory\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/category/put/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);


        Mockito.when(categoryService.updateCategory(Mockito.anyLong(), Mockito.any(Category.class)))
                .thenReturn(new Category(1L, "UpdatedTestCategory"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("UpdatedTestCategory")))
                .andExpect(header().string("Custom-Header", "Category Updated Successfully"));
    }

    @Test
    public void testUpdateCategoryNotFound() throws Exception {
        String jsonRequest = "{\"name\": \"UpdatedTestCategory\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/category/put/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        Mockito.when(categoryService.updateCategory(Mockito.anyLong(), Mockito.any(Category.class)))
                .thenReturn(null);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(header().string("Custom-Header", "Category Not Found"));
    }

    @Test
    void testUpdateCategoryWithInvalidId() throws Exception {
        Long categoryId = 456L;
        Category category = new Category();
        category.setName("Test Category");

        Mockito.when(categoryService.updateCategory(Mockito.eq(categoryId), Mockito.any(Category.class)))
                .thenThrow(new ResourceNotFoundException("Category not found"));

        mockMvc.perform(put("/category/put/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isNotFound());
    }
}
