package com.zopsmart.assignment3.Controller;

import com.zopsmart.assignment3.Model.*;
import com.zopsmart.assignment3.Service.ProductService;

import java.util.Scanner;


/**
 * ProductController For taking Product Input and inputs choice for CRUD operations
 */
public class ProductController {

    /**
     * addProduct function to take user input to add particular product
     */

     ProductService service=new ProductService();

    public void addProduct(Scanner scn) {
        String choice;
        System.out.println("Enter productID: ");
        int productID = scn.nextInt();

        System.out.println("Enter Product Name: ");
        String productName = scn.next();

        System.out.println("Enter Product Description: ");
        String description = scn.next();

        System.out.println("Enter price: ");
        double price = scn.nextDouble();

        do {
            System.out.println("\nChoose from the below: ");
            System.out.println("Laptops to Add a Laptop ");
            System.out.println("FreshGrocery to Add a FreshGrocery item");
            System.out.println("Soap To Add a Soap item");
            System.out.println("Exit to stop");
            choice = scn.next();

            switch (choice) {
                case "Laptops":
                    System.out.println("Enter Brand Name: ");
                    String brand = scn.next();

                    System.out.println("Enter Product Category: ");
                    String category = scn.next();

                    System.out.println("Enter screenSize of laptop: ");
                    String screenSize = scn.next();

                    System.out.println("Enter processor: ");
                    String processor = scn.next();

                    Product p1 = new Laptops(productID, productName, description, price, brand, category, screenSize, processor);
                    service.addProduct(productID, p1);
                    break;

                case "FreshGrocery":
                    System.out.println("Enter Brand Name: ");
                    brand = scn.next();

                    System.out.println("Enter Product Category: ");
                    category = scn.next();

                    System.out.println("Enter name of the Fresh Grocery: ");
                    String name = scn.next();

                    System.out.println("Enter expiry Date of Fresh Grocery: ");
                    String expiryDate = scn.next();

                    Product p2 = new FreshGrocery(productID, productName, description, price, brand, category, expiryDate, name);
                    service.addProduct(productID, p2);
                    break;

                case "Soap":
                    System.out.println("Enter Brand Name: ");
                    brand = scn.next();

                    System.out.println("Enter flavour of the soap: ");
                    String flavour = scn.next();

                    Product p3 = new Soap(productID, productName, description, price, flavour, brand);
                    service.addProduct(productID, p3);
                    break;
                case "Exit":
                    break;
                default:
                    System.out.println("Invalid Choice");

            }
        } while (!choice.equals("Exit"));
    }

    /**
     * updateProduct function to take user input to update particular product
     */
    public void updateProduct(Scanner scn) {
        String choice;
        System.out.println("Enter productID: ");
        int productID = scn.nextInt();

        System.out.println("Enter Product Name: ");
        String productName = scn.next();

        System.out.println("Enter Product Description: ");
        String description = scn.next();

        System.out.println("Enter price: ");
        double price = scn.nextDouble();
        do {
            System.out.println("\nChoose from the below: ");
            System.out.println("Laptops to Update a Laptop item ");
            System.out.println("FreshGrocery to Update a FreshGrocery item");
            System.out.println("Soap To Update a Soap item");
            System.out.println("Exit to stop");
            choice = scn.next();

            switch (choice) {
                case "Laptops":
                    System.out.println("Enter Brand Name: ");
                    String brand = scn.next();

                    System.out.println("Enter Product Category: ");
                    String category = scn.next();

                    System.out.println("Enter screenSize of laptop: ");
                    String screenSize = scn.next();

                    System.out.println("Enter processor: ");
                    String processor = scn.next();

                    Product p1 = new Laptops(productID, productName, description, price, brand, category, screenSize, processor);
                    service.updateProduct(productID, p1);
                    break;

                case "FreshGrocery":

                    System.out.println("Enter Brand Name: ");
                    brand = scn.next();

                    System.out.println("Enter Product Category: ");
                    category = scn.next();

                    System.out.println("Enter name of the Fresh Grocery: ");
                    String name = scn.next();

                    System.out.println("Enter expiry Date of Fresh Grocery: ");
                    String expiryDate = scn.next();

                    Product p2 = new FreshGrocery(productID, productName, description, price, brand, category, expiryDate, name);
                    service.updateProduct(productID, p2);
                    break;

                case "Soap":
                    System.out.println("Enter Brand Name: ");
                    brand = scn.next();

                    System.out.println("Enter flavour of the soap: ");
                    String flavour = scn.next();

                    Product p3 = new Soap(productID, productName, description, price, flavour, brand);
                    service.updateProduct(productID, p3);
                    break;
                case "Exit":
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != "Exit");
    }

    /**
     * productInput function to take Product Input and inputs choice for CRUD operations
     */
    public void productInput() {

        Scanner scn = new Scanner(System.in);
        int productID;
        int option;

        do {
            System.out.println("\nChoose from the below: ");
            System.out.println("1. Add a product");
            System.out.println("2. Get product by Id");
            System.out.println("3. Delete product");
            System.out.println("4. Get ALL");
            System.out.println("5. update Product");
            System.out.println("6.exit ");
            System.out.print("Enter your choice: ");
            option = scn.nextInt();

            switch (option) {
                case 1:
                    addProduct(scn);
                    break;
                case 2:
                    int choice;
                    do {
                        System.out.println("\nChoose from the below: ");
                        System.out.println("1. Find an Electronic item");
                        System.out.println("2. Find a Grocery item");
                        System.out.println("3. Find a PersonalCare item");
                        System.out.println("4. Exit");
                        choice = scn.nextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("Enter productID: ");
                                productID = scn.nextInt();
                                service.getById(productID);
                                break;

                            case 2:
                                System.out.println("Enter productId: ");
                                productID = scn.nextInt();
                                service.getById(productID);
                                break;

                            case 3:
                                System.out.println("Enter ProductID: ");
                                productID = scn.nextInt();
                                service.getById(productID);
                                break;
                            default: {
                                System.out.println("invalid choice");
                            }

                        }
                    } while (choice != 4);
                    break;

                case 3:
                    System.out.println("Enter productID: ");
                    productID = scn.nextInt();
                    service.deleteProduct(productID);
                    break;
                case 4:
                    System.out.println("\n");
                    service.getALL();
                    break;
                case 5:
                    updateProduct(scn);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        } while (option != 6);
    }
}
