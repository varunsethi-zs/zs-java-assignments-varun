package com.zopsmart.assignment3.Service;

import com.zopsmart.assignment3.Model.*;

import java.util.HashMap;

/**
 * ProductService Class for Crud Implementations
 */
public class ProductService {

    public static HashMap<Integer, Product> categoryMap = new HashMap<>();

    /**
     * addProduct function to add a new Product
     */

    public void addProduct(int productID, Product p) {
        categoryMap.put(productID, p);
    }

    /**
     * DeleteProduct function for deleting Product by id
     */
    public void deleteProduct(int productId) {

        if (!categoryMap.containsKey(productId)) {
            System.out.println("product does not exists");
        } else {
            this.categoryMap.remove(productId);
            System.out.println("product with product id " + productId + "deleted");
        }
    }


    /**
     * getALL function for getting all Product
     */
    public void getALL() {
        for (int id : categoryMap.keySet()) {
            Product product = categoryMap.get(id);
            product.displayProducts();
            System.out.println("\n");
        }
    }


    /**
     * getById function to get Product through ProductId
     */
    public void getById(int productID) {
        if (!categoryMap.containsKey(productID)) {
            System.out.println("product does not exists");
        } else {
            Product product = categoryMap.get(productID);
            product.displayProducts();
            System.out.println("\n");
        }
    }


    /**
     * updateProduct function to update a Product product
     */
    public void updateProduct(int productID, Product product) {
        if (!categoryMap.containsKey(productID)) {
            System.out.println("product does not exists");
        } else {
            categoryMap.replace(productID, product);
        }
    }
}
