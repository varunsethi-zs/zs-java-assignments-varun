package com.zopsmart.assignment11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


/**
 * ProductStoreApplication class to run spring boot application
 */
@SpringBootApplication
public class ProductStoreApplication {

    /**
     * main function to run spring boot application
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductStoreApplication.class, args);
    }

}
