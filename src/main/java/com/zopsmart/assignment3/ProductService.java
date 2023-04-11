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
     * addElectronic function for adding electronic Product
     */

    public void addElectronic(int productID, Product p) {
        map.put(productID, p);
    }

    /**
     * addGrocery function for adding Grocery Product
     */

    public void addGrocery(int productID, Product p) {
        map.put(productID, p);
    }

    /**
     * addPersonalCare function for adding PersonalCare Product
     */
    public void addPersonalCare(int productID, Product p) {
        map.put(productID, p);
    }


    /**
     * findElectronic function for getting electronic Product by id
     */

    public void findElectronic(int productID) {
        if (!map.containsKey(productID)) {
            System.out.println("product does not exists");
        } else {
            Laptops laptops = (Laptops) map.get(productID);

            System.out.println("Product ID: " + laptops.productId);
            System.out.println("Product price: " + laptops.productPrice);
            System.out.println("Product brand: " + laptops.brand);
            System.out.println("Laptop's screen size: " + laptops.screenSize);
            System.out.println("Laptop's processor is : " + laptops.processor);
        }
    }

    /**
     * findGrocery for getting Grocery Product by id
     */
    public void findGrocery(int productID) {
        if (!map.containsKey(productID)) {
            System.out.println("product does not exists");
        } else {
            FreshGrocery freshGrocery = (FreshGrocery) map.get(productID);

            System.out.println("Product ID: " + freshGrocery.productId);
            System.out.println("Product price: " + freshGrocery.productPrice);
            System.out.println("Grocery brand: " + freshGrocery.brand);
            System.out.println("Grocery Category: " + freshGrocery.category);
            System.out.println("Grocery name: " + freshGrocery.name);
            System.out.println("Grocery expiry Date: " + freshGrocery.expiryDate);

        }
    }

    /**
     * findEPersonalCare function for getting PersonalCare Product by id
     */

    public void findPersonalCare(int productID) {
        if (!map.containsKey(productID)) {
            System.out.println("product does not exists");
        } else {

            Soap soap = (Soap) map.get(productID);

            System.out.println("Product ID: " + soap.productId);
            System.out.println("Product price: " + soap.productPrice);
            System.out.println("Company of the soap: " + soap.brand);
            System.out.println("Flavour of the soap: " + soap.flavour);
        }
    }

    /**
     * DeleteProduct function for deleting Product by id
     */
    public void deleteProduct(int productId) {
        if (this.map.containsKey(productId)) {
            this.map.remove(productId);

            System.out.println("product with product id " + productId + "deleted");
        }
    }


    /**
     * getALL function for getting all Product
     */
    public void getALL() {
        for (int id : map.keySet()) {
            Product val = map.get(id);
            System.out.println("Product ID = " + val.productId);
            System.out.println("Product name = " + val.productName);
            System.out.println("Product price = " + val.productPrice);
        }
    }
}

