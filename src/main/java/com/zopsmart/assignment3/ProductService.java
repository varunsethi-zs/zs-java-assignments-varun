package com.zopsmart.assignment3;

import com.zopsmart.assignment3.Model.*;

import java.util.HashMap;
import java.util.Iterator;

/**
 * ProductService Class for Crud Implementations
 */
public class ProductService {

    public static HashMap<Integer, Product> map = new HashMap<>();

    /**
     * addProduct function to add a new Product
     */

    public void addProduct(int productID, Product p) {
        map.put(productID, p);
    }

    /**
     * DeleteProduct function for deleting Product by id
     */
    public void deleteProduct(int productId) {

        if (!map.containsKey(productId)) {
            System.out.println("product does not exists");
        } else {
            this.map.remove(productId);
            System.out.println("product with product id " + productId + "deleted");
        }
    }


    /**
     * getALL function for getting all Product
     */
    public void getALL() {
        for (int id : map.keySet()) {
            Product product = map.get(id);
            product.displayProducts();
            System.out.println("\n");
        }
    }


    /**
     * getById function to get Product through ProductId
     */
    public void getById(int productID) {
        if (!map.containsKey(productID)) {
            System.out.println("product does not exists");
        } else {
            Product product = map.get(productID);
            product.displayProducts();
            System.out.println("\n");
        }
    }


    /**
     * updateProduct function to update a Product product
     */
    public void updateProduct(int productID, Product product) {
        if (!map.containsKey(productID)) {
            System.out.println("product does not exists");
        } else {
            map.replace(productID, product);
        }
    }
}
