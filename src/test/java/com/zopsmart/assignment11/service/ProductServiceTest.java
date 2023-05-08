package com.zopsmart.assignment11.service;

import com.zopsmart.assignment11.dao.CategoryDao;
import com.zopsmart.assignment11.dao.ProductDao;
import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.entity.Product;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    private Product product;
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category("Electronics");
        categoryDao.save(category);

        product = new Product("Laptop", 50000.00, category);
        productDao.save(product);
    }

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        productDao.deleteAll();
        categoryDao.deleteAll();
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product.getName(), products.get(0).getName());
        assertEquals(product.getPrice(), products.get(0).getPrice(), 0.001);
        assertEquals(product.getCategory().getName(), products.get(0).getCategory().getName());
    }

    @Test
    public void testGetProductById() {
        Optional<Product> optionalProduct = productService.getProductById(product.getId());
        assertTrue(optionalProduct.isPresent());
        Product foundProduct = optionalProduct.get();
        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(product.getPrice(), foundProduct.getPrice());
        assertEquals(product.getCategory().getName(), foundProduct.getCategory().getName());
    }

    @Test
    public void testGetProductsByCategory() throws ResourceNotFoundException {
        List<Product> products = productService.getProductsByCategory(category.getName());
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product.getName(), products.get(0).getName());
        assertEquals(product.getPrice(), products.get(0).getPrice(), 0.001);
        assertEquals(product.getCategory().getName(), products.get(0).getCategory().getName());
    }

    @Test
    void getProductByCategory_shouldThrowIllegalArgumentException_whenCategoryNotFound() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductsByCategory("Non-existing category");
        });

        assertEquals("Category does not exists", exception.getMessage());
    }


    @Test
    void testCreateProduct() {

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("Laptop", savedProduct.getName());
        assertEquals(50000.00, savedProduct.getPrice());
        assertEquals("Electronics", savedProduct.getCategory().getName());
    }

    @Test
    void testCreateProductWithInvalidParameters() {

        Category category = new Category("Electronics");
        Product product = new Product(null, -1000.00, category);
        assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(product);
        });
    }

    @Test
    void testCreateProductWithExistingCategory() {
        Product product = new Product("Smartphone", 15000.00, category);

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("Smartphone", savedProduct.getName());
        assertEquals("Electronics", savedProduct.getCategory().getName());
        assertEquals(15000.00, savedProduct.getPrice());
    }

    @Test
    void testCreateProductWithNewCategory() {
        Category category = new Category("Clothing");
        categoryDao.save(category);
        Product product = new Product("Shirt", 2900.99, category);

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("Shirt", savedProduct.getName());
        assertEquals("Clothing", savedProduct.getCategory().getName());
        assertEquals(2900.99, savedProduct.getPrice());
        List<Category> categories = categoryDao.findByName("Clothing");
        assertEquals(1, categories.size());
        assertEquals("Clothing", categories.get(0).getName());
    }

    @Test
    public void testDeleteProduct() throws ResourceNotFoundException {
        productService.deleteProduct(product.getId());
        Optional<Product> optionalProduct = productDao.findById(product.getId());
        assertFalse(optionalProduct.isPresent());
    }

    @Test
    void testDeleteNonExistingProduct() {
        Long productId = -1L;
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));
    }

    @Test
    public void testUpdateProduct() {
        Category newCategory = new Category("Mobiles");
        Product updatedProduct = new Product("Mobile", 20000.00, newCategory);
        updatedProduct.setId(product.getId());
        productService.updateProduct(product.getId(), updatedProduct);
        Optional<Product> optionalProduct = productDao.findById(product.getId());
        assertTrue(optionalProduct.isPresent());
        Product foundProduct = optionalProduct.get();
        assertEquals(updatedProduct.getName(), foundProduct.getName());
        assertEquals(updatedProduct.getPrice(), foundProduct.getPrice());
        assertEquals(updatedProduct.getCategory().getName(), foundProduct.getCategory().getName());
    }

    @Test
    void testUpdateProductWithNonexistentId() {
        product = productDao.save(product);
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(product.getId() + 1, new Product());
        });
    }

    @Test
    public void testDoesProductExists() {
        assertTrue(productService.doesProductExists(product.getId()));
        assertFalse(productService.doesProductExists(-1L));
    }
}
