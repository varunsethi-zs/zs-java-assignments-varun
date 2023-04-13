package com.zopsmart.assignment3.Model;


/**
 * PersonalCare Class inherits Product class
 */
public class PersonalCare extends Product {

    /**
     * PersonalCare Parameterized Constructor
     */
    public PersonalCare(int productId, String productName, String productDescription, double productPrice) {
        super(productId, productName, productDescription, productPrice);
       isReturnable=false;
    }

    @Override
    public void displayProducts() {
        super.displayProducts();
    }
}
