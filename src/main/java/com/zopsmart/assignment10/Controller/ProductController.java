package com.zopsmart.assignment10.Controller;

import com.zopsmart.assignment10.Dao.ProductDao;
import com.zopsmart.assignment10.Service.ProductService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * ProductController class to call service layer functions
 */
public class ProductController {


    private final ProductDao productDao = new ProductDao();
    Logger logger = Logger.getLogger(ProductController.class.getName());
    ProductService productService = new ProductService(productDao);

    /**
     * setProductService function to take input from user and call service layer functions
     */
    public void setProductService() {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter Product Id");
        int productId = scanner.nextInt();
        try {
            productService.createProductTable();
            logger.info("Table created successfully");
            productService.saveProduct();
            logger.info("inserted the product values successfully");
            productService.getProductById(productId);
            productService.getAllProducts();
            boolean result = productService.doesProductExist(productId);
            logger.info("product exists :" + result);
            productService.updateProduct();
            productService.deleteProductById(productId);
            logger.info("deleted product successfully");
        } catch (SQLException sqlException) {
            logger.info(sqlException.getMessage());
        }
    }
}
