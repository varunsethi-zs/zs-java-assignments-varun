package com.zopsmart.assignment3.Model;


/**
 * PersonalCare Class inherits Product class
 */
public class PersonalCare extends Product {
    public boolean noReturn;

    /**
     * PersonalCare Parameterized Constructor
     */
    public PersonalCare(int productId, String productName, String productDescription, double productPrice, boolean noReturn) {
        super(productId, productName, productDescription, productPrice);
        this.noReturn = noReturn;
    }

    @Override
    public void displayProducts() {
        super.displayProducts();
    }
}
