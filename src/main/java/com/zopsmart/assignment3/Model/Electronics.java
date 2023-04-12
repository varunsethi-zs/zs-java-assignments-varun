package com.zopsmart.assignment3.Model;


/**
 * Electronics class inherits the Product class
 */
public class Electronics extends Product {
    public String brand;
    public String category;

    /**
     * Electronics Parameterized Constructor
     */
    public Electronics(int productId, String productName, String productDescription, double productPrice, String brand, String category) {
        super(productId, productName, productDescription, productPrice);
        this.brand = brand;
        this.category = category;
    }

    @Override
    public void displayProducts() {
        super.displayProducts();
        System.out.println("Brand " + brand);
        System.out.println("Category " + category);
    }
}


