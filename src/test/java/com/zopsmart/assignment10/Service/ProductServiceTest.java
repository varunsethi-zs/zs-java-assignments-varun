package com.zopsmart.assignment10.Service;

import com.zopsmart.assignment10.Dao.ProductDao;
import com.zopsmart.assignment10.Exception.ProductNotFoundException;
import com.zopsmart.assignment10.Model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        productService = new ProductService(productDao);
        product1 = new Product("television", 1, 440000.0, "electronics");
        product2 = new Product("Fruits", 2, 80.0, "grocerry");
    }

    @Test
    void testCreateProductTable() throws SQLException {
        // Call the createProductTable method
        doNothing().when(productDao).createProductTable();
        productService.createProductTable();
        verify(productDao, times(1)).createProductTable();
    }


    @Test
    public void testGetAllProducts() throws SQLException {

        List<Product> expectedProducts = Arrays.asList(product1, product2);
        when(productDao.findAll()).thenReturn(expectedProducts);
        List<Product> actualProducts = productService.getAllProducts();
        verify(productDao, times(1)).findAll();
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testGetProductById() throws SQLException {
        Product expectedProduct = new Product("television", 1, 440000.0, "electronics");
        when(productDao.findById(anyInt())).thenReturn(expectedProduct);
        Product actualProduct = productService.getProductById(1);
        verify(productDao, times(1)).findById(1);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testDeleteById() throws SQLException, ProductNotFoundException {
        productService.deleteProductById(1);
        verify(productDao, times(1)).deleteById(1);
    }

    @Test
    public void testExists() throws SQLException {
        int id = 1;
        when(productDao.exists(id)).thenReturn(true);
        boolean actualExists = productService.doesProductExist(id);
        assertTrue(actualExists);
        verify(productDao, times(1)).exists(id);
    }

    @Test
    void testUpdateProduct() throws SQLException, ProductNotFoundException {

        int id = product1.getId();
        Product updatedProduct = new Product("Mobile", id, 450000.0, "electronics");
        productService.updateProduct(updatedProduct, id);
        verify(productDao, times(1)).updateProduct(updatedProduct, id);
        assertEquals("Mobile", updatedProduct.getName());
        assertEquals(450000.0, updatedProduct.getPrice());
        assertEquals("electronics", updatedProduct.getDescription());
    }
    @Test
    void testSaveProduct() throws SQLException {
        productService.saveProduct(product1);
        verify(productDao,times(1)).saveProduct(product1);
    }



}
