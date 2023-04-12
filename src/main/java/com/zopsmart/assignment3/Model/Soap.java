package com.zopsmart.assignment3.Model;


/**
 * Soap Class inherits PersonalCare class
 */
public class Soap extends PersonalCare {

    public String flavour;
    public String brand;

    /**
     * Soap Parameterized Constructor
     */
    public Soap(int productId, String productName, String productDescription, double productPrice, boolean noReturn, String flavour, String brand) {
        super(productId, productName, productDescription, productPrice, noReturn);
        this.flavour = flavour;
        this.brand = brand;
    }

    @Override
    public void displayProducts() {
        super.displayProducts();
        System.out.println("flavour" + flavour);
        System.out.println("brand " + brand);
    }
}
