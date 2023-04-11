package com.zopsmart.assignment3.Model;


/**
 * Grocery Class inherits Product class
 */
public class Grocery extends Product {
    public String brand;
    public String category;

    /**
     * Grocery Parameterized Constructor
     */

    public Grocery(int productId, String productName, String productDescription, double productPrice, String brand, String category) {
        super(productId, productName, productDescription, productPrice);
        this.brand = brand;
        this.category = category;
    }

}