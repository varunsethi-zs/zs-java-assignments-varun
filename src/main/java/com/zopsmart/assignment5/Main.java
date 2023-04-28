package com.zopsmart.assignment5;

import com.zopsmart.assignment5.Controller.LogController;



/**
 * Main class for Calling Controller And its Functions
 */
public class Main {

    /**
     * main function for Calling Controller And its Functions
     */
    public static void main(String[] args) {
        LogController logController = new LogController();

        logController.logInputs();
    }
}
