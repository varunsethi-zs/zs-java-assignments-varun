package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.ProductStoreApplication;
import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.entity.Product;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import com.zopsmart.assignment11.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(
                "http://localhost:8080/products/get",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Product>>() {}
        );

        List<Product> productList = responseEntity.getBody();
        assertNotNull(productList);
        assertTrue(productList.size() > 0);
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product(productId, "Product 1", 10.0, new Category(1L, "Category 1"));

        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = restTemplate.getForEntity("/products/get/{id}", Product.class, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetProductByIdNotFound() {
        Long productId = 1L;

        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = restTemplate.getForEntity("/products/get/{id}", Product.class, productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetProductsByCategory() {
        String categoryName = "Category 1";
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product 1", 10.0, new Category(1L, categoryName)));
        products.add(new Product(2L, "Product 2", 20.0, new Category(1L, categoryName)));

        when(productService.getProductsByCategory(categoryName)).thenReturn(products);

        ResponseEntity<List<Product>> response = restTemplate.exchange("/products/{categoryName}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }, categoryName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testGetProductsByCategoryNotFound() {
        String categoryName = "Category 1";

        when(productService.getProductsByCategory(categoryName)).thenReturn(null);

        ResponseEntity<List<Product>> response = restTemplate.exchange("/products/{categoryName}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }, categoryName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(null, "Product 1", 10.0, new Category(1L, "Category 1"));
        Product createdProduct = new Product(1L, "Product 1", 10.0, new Category(1L, "Category 1"));

        when(productService.createProduct(product)).thenReturn(createdProduct);

        ResponseEntity<Product> response = restTemplate.postForEntity("/products/post", product, Product.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
    }

    @Test
    public void testCreateProductBadRequest() {
        Product product = new Product(null, null, 10.0, new Category(1L, "Category 1"));
        Mockito.when(productService.createProduct(Mockito.any(Product.class))).thenReturn(null);
        ResponseEntity<Product> response = restTemplate.postForEntity("/products/post", product, Product.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getHeaders().get("Custom-Header").get(0));
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(1L, "Product 1", 10.0, new Category(1L, "Category 1"));
        Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.any(Product.class))).thenReturn(product);
        ResponseEntity<Product> response = restTemplate.exchange("/products/1", HttpMethod.PUT, new HttpEntity<>(product), Product.class);
      assertEquals(HttpStatus.OK, response.getStatusCode());
     assertEquals("Product Updated Successfully", response.getHeaders().get("Custom Header").get(0));
     assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProductNotFound() {
        Product product = new Product(1L, "Product 1", 10.0, new Category(1L, "Category 1"));
        Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.any(Product.class))).thenReturn(null);
        ResponseEntity<Product> response = restTemplate.exchange("/products/1", HttpMethod.PUT, new HttpEntity<>(product), Product.class);
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     assertEquals("Product not found", response.getHeaders().get("Custom-Header").get(0));
    }

    @Test
    public void testDeleteProduct() throws ResourceNotFoundException {
        Mockito.doNothing().when(productService).deleteProduct(Mockito.anyLong());
        ResponseEntity<String> response = restTemplate.exchange("/products/1", HttpMethod.DELETE, null, String.class);
       assertEquals(HttpStatus.OK, response.getStatusCode());
     assertEquals("Product with id 1 deleted successfully", response.getBody());
    }

}