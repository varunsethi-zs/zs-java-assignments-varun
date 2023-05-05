package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void getAllCategories_shouldReturnAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Books"));
        categories.add(new Category(2L, "Electronics"));

        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(categories.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(categories.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(categories.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(categories.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(categories.get(1).getName()));
    }
}