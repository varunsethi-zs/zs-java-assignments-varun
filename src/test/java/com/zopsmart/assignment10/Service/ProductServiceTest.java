package com.zopsmart.assignment10.Service;

import com.zopsmart.assignment10.Dao.ProductDao;
import com.zopsmart.assignment10.Model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @BeforeEach
    public void setup() {
        productService = new ProductService(productDao);
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        Product product1 = new Product("television",1, 440000.0,"electronics");
        Product product2 = new Product("Fruits",2, 80.0,"grocerry");
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        when(productDao.findAll()).thenReturn(expectedProducts);
        List<Product> actualProducts = productService.getAllProducts();
        verify(productDao).findAll();
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testGetProductById() throws SQLException {
        Product expectedProduct = new Product("television",1, 440000.0,"electronics");
        when(productDao.findById(1)).thenReturn(expectedProduct);
        Product actualProduct = productService.getProductById(1);
        verify(productDao).findById(1);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testDeleteById() throws SQLException {
        int id = 1;
        productService.deleteProductById(id);
        verify(productDao).deleteById(id);
    }

    @Test
    public void testExists() throws SQLException {
        int id = 1;
        when(productDao.exists(id)).thenReturn(true);
        boolean actualExists = productService.doesProductExist(id);
        assertTrue(actualExists);
        verify(productDao).exists(id);
    }

}
