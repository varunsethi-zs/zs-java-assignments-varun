package com.zopsmart.assignment3.Model;

/**
 * Product Parent Class
 */
public class Product {
    public int productId;
    public String productName;
    public String productDescription;
    public double productPrice;

    /**
     * Product Parameterized Constructor
     */

    public Product(int productId, String productName, String productDescription, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public void displayProducts() {
        System.out.println("productId " + productId);
        System.out.println("productName " + productName);
        System.out.println("productPrice " + productPrice);
    }
}

