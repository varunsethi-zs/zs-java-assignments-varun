package com.zopsmart.assignment3;

import com.zopsmart.assignment3.Controller.ProductController;

/**
 * ProductMain Class For Calling Controller And Product Input function
 */
public class ProductMain {

    public static void main(String[] args) {

        ProductController productController = new ProductController();
        productController.productInput();
    }
}
