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
        assertEquals(product.getPrice(), foundProduct.getPrice(), 0.001);
        assertEquals(product.getCategory().getName(), foundProduct.getCategory().getName());
    }

    @Test
    public void testGetProductsByCategory() {
        List<Product> products = productService.getProductsByCategory(category.getName());
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product.getName(), products.get(0).getName());
        assertEquals(product.getPrice(), products.get(0).getPrice(), 0.001);
        assertEquals(product.getCategory().getName(), products.get(0).getCategory().getName());
    }

    @Test
    public void testCreateProduct() {
        Category newCategory = new Category("Books");
        categoryDao.save(newCategory); // save category to generate an id
        Product newProduct = new Product("Book", 100.00, newCategory);
        productService.createProduct(newProduct);
        assertNotNull(newProduct.getId());
        Optional<Product> optionalProduct = productDao.findById(newProduct.getId());
        assertTrue(optionalProduct.isPresent());
        Product foundProduct = optionalProduct.get();
        assertEquals(newProduct.getName(), foundProduct.getName());
        assertEquals(newProduct.getPrice(), foundProduct.getPrice(), 0.001);
        assertEquals(newProduct.getCategory().getName(), foundProduct.getCategory().getName());
    }


    @Test
    public void testDeleteProduct() throws ResourceNotFoundException {
        productService.deleteProduct(product.getId());
        Optional<Product> optionalProduct = productDao.findById(product.getId());
        assertFalse(optionalProduct.isPresent());
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
        assertEquals(updatedProduct.getPrice(), foundProduct.getPrice(), 0.001);
        assertEquals(updatedProduct.getCategory().getName(), foundProduct.getCategory().getName());
    }

    @Test
    public void testDoesProductExists() {
        assertTrue(productService.doesProductExists(product.getId()));
        assertFalse(productService.doesProductExists(-1L));
    }

}
