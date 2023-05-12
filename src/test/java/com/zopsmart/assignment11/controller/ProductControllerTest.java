package com.zopsmart.assignment11.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.entity.Product;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import com.zopsmart.assignment11.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Tv", 100000.0, new Category(1L, "Electronics")));
        products.add(new Product(2L, "Milk", 100.0, new Category(2L, "Dairy")));

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(get("/products/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Tv")))
                .andExpect(jsonPath("$[0].price", is(100000.0)))
                .andExpect(jsonPath("$[0].category.id", is(1)))
                .andExpect(jsonPath("$[0].category.name", is("Electronics")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Milk")))
                .andExpect(jsonPath("$[1].price", is(100.0)))
                .andExpect(jsonPath("$[1].category.id", is(2)))
                .andExpect(jsonPath("$[1].category.name", is("Dairy")))
                .andExpect(header().string("Custom Header", "Product Found Successfully"));
    }

    @Test
    public void testGetAllProductsReturns404NotFound() {
        when(productService.getAllProducts()).thenReturn(null);
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Custom-Header"));
    }


    @Test
    public void testGetProductById() throws Exception {
        Long id = 1L;
        Product product = new Product(id, "TV", 100000.0, new Category(1L, "Electronics"));
        Optional<Product> optionalProduct = Optional.of(product);

        when(productService.getProductById(id)).thenReturn(optionalProduct);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(get("/products/get/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is("TV")))
                .andExpect(jsonPath("$.price", is(100000.0)))
                .andExpect(jsonPath("$.category.id", is(1)))
                .andExpect(jsonPath("$.category.name", is("Electronics")))
                .andExpect(header().string("Custom Header", "Product Found Successfully"))
                .andExpect(header().doesNotExist("Custom-Header"));
    }

    @Test
    public void testGetProductByIdReturns404NotFound() throws Exception {
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        mockMvc.perform(get("/products/get/{id}", productId))
                .andExpect(status().isNotFound())
                .andExpect(header().string("Custom-Header", "Product not found"));
    }


    @Test
    public void testGetProductsByCategory() throws Exception {
        String categoryName = "Electronics";
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "TV", 100000.0, new Category(1L, categoryName)));
        products.add(new Product(2L, "Laptop", 200000.0, new Category(2L, categoryName)));

        when(productService.getProductsByCategory(categoryName)).thenReturn(products);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(get("/products/{categoryName}", categoryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("TV")))
                .andExpect(jsonPath("$[0].price", is(100000.0)))
                .andExpect(jsonPath("$[0].category.id", is(1)))
                .andExpect(jsonPath("$[0].category.name", is(categoryName)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Laptop")))
                .andExpect(jsonPath("$[1].price", is(200000.0)))
                .andExpect(jsonPath("$[1].category.id", is(2)))
                .andExpect(jsonPath("$[1].category.name", is(categoryName)))
                .andExpect(header().string("Custom Header", "Product Found Successfully"))
                .andExpect(header().doesNotExist("Custom-Header"));
    }


    @Test
    public void testPostProduct() throws Exception {

        Product product = new Product();
        product.setName("Tv");
        product.setPrice(100000.0);
        Category category = new Category();
        category.setName("Electronics");
        category.setId(1L);
        product.setCategory(category);

        Product addedProduct = new Product();
        addedProduct.setId(1L);
        addedProduct.setName("Tv");
        addedProduct.setPrice(100000.0);
        addedProduct.setCategory(category);

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(addedProduct);


        mockMvc.perform(MockMvcRequestBuilders.post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addedProduct)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Custom-Header", "Product added successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tv"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100000.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value("Electronics"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(1));

    }


    @Test
    public void updateProductTest() throws Exception {
        Long productId = 1L;
        Product updatedProduct = new Product(productId, "Smartphone", 50000.0, new Category(1L, "Electronics"));
        Mockito.when(productService.updateProduct(Mockito.any(Long.class), Mockito.any(Product.class)))
                .thenReturn(updatedProduct);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom Header", "Product Updated Successfully");
        ResponseEntity<Product> expectedResponse = ResponseEntity.status(HttpStatus.OK).headers(headers).body(updatedProduct);
        ResponseEntity<Product> actualResponse = productController.updateProduct(productId, updatedProduct);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void updateProductNotFoundTest() throws Exception {
        Long productId = 1L;
        Product updatedProduct = new Product(productId, "Smartphone", 50000.0, new Category(1L, "Electronics"));
        Mockito.when(productService.updateProduct(Mockito.any(Long.class), Mockito.any(Product.class)))
                .thenReturn(null);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "Product not found");
        ResponseEntity<Product> expectedResponse = ResponseEntity.notFound().headers(headers).build();
        ResponseEntity<Product> actualResponse = productController.updateProduct(productId, updatedProduct);
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    public void testDeleteProduct() throws Exception {

        doNothing().when(productService).deleteProduct(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product with id 1 deleted successfully"));

        verify(productService, times(1)).deleteProduct(1L);
    }


}
