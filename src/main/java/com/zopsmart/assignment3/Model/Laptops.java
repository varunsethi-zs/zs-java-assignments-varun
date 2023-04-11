package com.zopsmart.assignment3.Model;


/**
 * Laptops Class inherits Electronics class
 */
public class Laptops extends Electronics {
    public String screenSize;
    public String processor;


    /**
     * Laptops Parameterized Constructor
     */
    public Laptops(int productId, String productName, String productDescription, double productPrice, String brand, String category, String screenSize, String processor) {
        super(productId, productName, productDescription, productPrice, brand, category);
        this.screenSize = screenSize;
        this.processor = processor;
    }

}
