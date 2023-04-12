package com.zopsmart.assignment3.Model;

/**
 * FreshGrocery Class inherits Grocery class
 */
public class FreshGrocery extends Grocery {
    public String name;
    public String expiryDate;


    /**
     * FreshGrocery Parameterized Constructor
     */


    public FreshGrocery(int productId, String productName, String productDescription, double productPrice, String brand, String category, String expiryDate, String name) {
        super(productId, productName, productDescription, productPrice, brand, category);
        this.expiryDate = expiryDate;
        this.name = name;
    }

    @Override
    public void displayProducts() {
        super.displayProducts();
        System.out.println("Name " + name);
        System.out.println("expiryDate " + expiryDate);
    }
}

