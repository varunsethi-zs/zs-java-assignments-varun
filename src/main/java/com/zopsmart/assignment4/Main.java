package com.zopsmart.assignment4;


import com.zopsmart.assignment4.Controller.EcommerceController;

/**
 * Main class for calling controller class
 */
public class Main {

    /**
     * main function for calling inputCategory function from controller
     */
    public static void main(String[] args) {

        EcommerceController ecommerceController = new EcommerceController();
        ecommerceController.inputCategory();
    }
}
