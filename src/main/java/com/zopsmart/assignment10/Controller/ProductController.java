package com.zopsmart.assignment10.Controller;

import com.zopsmart.assignment10.Dao.ProductDao;
import com.zopsmart.assignment10.Exception.ProductNotFoundException;
import com.zopsmart.assignment10.Model.Product;
import com.zopsmart.assignment10.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * ProductController class to call service layer functions
 */
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class.getName());
    private final ProductDao productDao = new ProductDao();

    ProductService productService = new ProductService(productDao);

    /**
     * productServiceOperations function to take input from user and call service layer functions
     */
    public void productServiceOperations() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            logger.info("Choose fromm below options");
            logger.info("1.To create table");
            logger.info("2.To enter product in table");
            logger.info("3.To fetch product details through id from product table");
            logger.info("4.To fetch product details from product table");
            logger.info("5.To check whether product exists or not");
            logger.info("6.To update product details from product table through id");
            logger.info("7.To delete product details from product table through id");
            logger.info("8.To exit");

            logger.info("enter choice:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    try {
                        productService.createProductTable();
                        logger.info("Table created successfully");
                        break;
                    } catch (SQLException e) {
                        logger.error("Error in creating table:" + e);
                    }
                case 2:
                    try {
                        logger.info("Enter Product Id");
                        int productId = scanner.nextInt();
                        logger.info("Enter product name");
                        String name = scanner.next();
                        logger.info("enter price for product");
                        Double price = scanner.nextDouble();
                        logger.info("enter product description");
                        String description = scanner.next();
                        Product product = new Product(name, description, price);
                        productService.saveProduct(product);
                        logger.info("inserted the product values successfully");
                        break;
                    } catch (SQLException e) {
                        logger.error("Error in inserting product values:" + e);
                    }
                case 3:
                    try {
                        logger.info("Enter id to fetch product details");
                        int productId = scanner.nextInt();
                        Product category = productService.getProductById(productId);
                        logger.info("category" + category.toString());
                        break;
                    } catch (SQLException e) {
                        logger.error("Error in fetching product details:" + e);
                    }
                case 4:
                    try {
                        List<Product> category = productService.getAllProducts();
                        for (Product value : category) {
                            logger.info(value.toString());
                        }
                        break;
                    } catch (SQLException e) {
                        logger.error("Error in fetching product details:" + e);
                    }
                case 5:
                    try {
                        logger.info("Enter id to check whether product exists or not");
                        int productId = scanner.nextInt();
                        boolean result = productService.doesProductExist(productId);
                        logger.info("product exists :" + result);
                        break;
                    } catch (SQLException e) {
                        logger.error("Id not found" + e);
                    }
                case 6:
                    try {
                        logger.info("Enter id to update product details");
                        int productId = scanner.nextInt();
                        logger.info("Enter product name");
                        String name = scanner.next();
                        logger.info("enter price for product");
                        Double price = scanner.nextDouble();
                        logger.info("enter product description");
                        String description = scanner.next();
                        Product product = new Product(name, description, price);
                        productService.updateProduct(product, productId);
                        logger.info("Updated product details successfully");
                        break;
                    } catch (SQLException e) {
                        logger.error("Error in updating product details");
                    } catch (ProductNotFoundException e) {
                      logger.error("Product id not found"+e.getMessage());
                    }
                case 7:
                    try {
                        logger.info("Enter id to delete product details");
                        int productId = scanner.nextInt();
                        productService.deleteProductById(productId);
                        logger.info("deleted product successfully");
                        break;
                    } catch (SQLException | ProductNotFoundException e) {
                        logger.error("id not found :" + e);
                    }
                case 8:
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }

        } while (choice != 8);
    }
}
