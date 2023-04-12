package com.zopsmart.assignment3;

import com.zopsmart.assignment3.Model.*;

import java.util.Scanner;


/**
 * ProductController For taking Product Input and inputs choice for CRUD operations
 */
public class ProductController {

    /**
     * productInput function to take Product Input and inputs choice for CRUD operations
     */
    public void productInput() {

        ProductService service = new ProductService();
        Scanner scn = new Scanner(System.in);
        int productID;
        String productName;
        String description;
        Double price;
        int option;

        do {
            System.out.println("\nChoose from the below: ");
            System.out.println("1. Add a product");
            System.out.println("2. Find a product");
            System.out.println("3. Delete product");
            System.out.println("4. Get ALL");
            System.out.println("5.exit ");
            System.out.print("Enter your choice: ");
            option = scn.nextInt();

            switch (option) {
                case 1:
                    int choice;

                    do {
                        System.out.println("\nChoose from the below: ");
                        System.out.println("1. Add an Electronic item");
                        System.out.println("2. Add a Grocery item");
                        System.out.println("3. Add a PersonalCare item");
                        System.out.println("4. Exit");
                        choice = scn.nextInt();

                        switch (choice) {
                            case 1:
                                System.out.println("Enter productID: ");
                                productID = scn.nextInt();

                                System.out.println("Enter Product Name: ");
                                productName = scn.next();

                                System.out.println("Enter Product Description: ");
                                description = scn.next();

                                System.out.println("Enter price: ");
                                price = scn.nextDouble();

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

                            case 2:
                                System.out.println("Enter productID: ");
                                productID = scn.nextInt();

                                System.out.println("Enter Product Name: ");
                                productName = scn.next();

                                System.out.println("Enter Product Description: ");
                                description = scn.next();

                                System.out.println("Enter price: ");
                                price = scn.nextDouble();

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

                            case 3:
                                System.out.println("Enter productID: ");
                                productID = scn.nextInt();

                                System.out.println("Enter Product Name: ");
                                productName = scn.next();

                                System.out.println("Enter Product Description: ");
                                description = scn.next();

                                System.out.println("Enter price: ");
                                price = scn.nextDouble();


                                System.out.println("Enter Returable value: ");
                                boolean returnable = scn.nextBoolean();


                                System.out.println("Enter Brand Name: ");
                                brand = scn.next();


                                System.out.println("Enter flavour of the soap: ");
                                String flavour = scn.next();

                                Product p3 = new Soap(productID, productName, description, price, returnable, flavour, brand);
                                service.addProduct(productID, p3);
                                break;
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    } while (choice != 4);
                    break;
                case 2:
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
                                service.findElectronic(productID);
                                break;

                            case 2:
                                System.out.println("Enter productID: ");
                                productID = scn.nextInt();
                                service.findGrocery(productID);
                                break;

                            case 3:
                                System.out.println("Enter productID: ");
                                productID = scn.nextInt();
                                service.findPersonalCare(productID);
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
                    service.getALL();
                    break;
                default:{
                    System.out.println("Invalid Choice");
                    break;
                }
            }
        } while (option != 5);

    }
}
