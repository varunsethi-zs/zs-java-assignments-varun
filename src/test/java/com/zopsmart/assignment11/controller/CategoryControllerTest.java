package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

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

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllCategories_Success() throws Exception {
        Category category1 = new Category(1L, "Category 1");
        Category category2 = new Category(2L, "Category 2");
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
    public void testGetAllCategories_InternalServerError() throws Exception {
        when(categoryService.getAllCategories()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/category/get"))
                .andExpect(status().isInternalServerError());
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
    public void testPostCategoryWithExistingName() throws Exception {
        Category category = new Category();
        category.setName("Books");

        Mockito.when(categoryService.addCategory(Mockito.any(Category.class)))
                .thenThrow(new IllegalArgumentException("Category name already exists"));


        mockMvc.perform(MockMvcRequestBuilders.post("/category/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.header().string("Custom-Header", "Category name already exists"));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        Long categoryId = 1L;
        Category category = new Category();
        category.setName("Books");
        Category addedCategory = categoryService.addCategory(category);

        when(categoryService.getCategoryById(categoryId)).thenReturn(addedCategory);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/get/{categoryId}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedCategory.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(addedCategory.getName()))
                .andReturn();

        String customHeader = result.getResponse().getHeader("Custom-Header");
        assertEquals("Category Found Successfully", customHeader);
    }
}
