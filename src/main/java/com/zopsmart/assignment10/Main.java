package com.zopsmart.assignment10;

import com.zopsmart.assignment10.Controller.ProductController;

import java.sql.*;

public class Main {

    /**
     * main function to call controller layer functions
     */
    public static void main(String[] args) throws SQLException {

        ProductController productController = new ProductController();
        productController.setProductService();

    }
}
