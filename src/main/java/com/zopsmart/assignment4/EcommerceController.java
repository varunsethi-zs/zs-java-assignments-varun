package com.zopsmart.assignment4;

import com.zopsmart.assignment4.Model.Category;
import com.zopsmart.assignment4.Model.LruCache;

import java.util.Scanner;

/**
 * EcommerceController class for taking input
 */
public class EcommerceController {

    /**
     * inputCategory function for taking inputs
     */
    public void inputCategory() {
        EcommerceService ecommerceService = new EcommerceService();
        Scanner scn = new Scanner(System.in);
        LruCache lruCache = new LruCache(6);
        int option;

        do {
            System.out.println("\nChoose from the below: ");
            System.out.println("1. Add and Display The category");
            System.out.println("2. To get category");
            System.out.println("3. exit ");
            System.out.print(" Enter your choice: ");
            option = scn.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter the name of the root category: ");
                    String rootName = scn.next();
                    Category root = new Category(rootName);

                    while (true) {

                        System.out.print("Enter the name of a category to add or finish:");
                        String categoryName = scn.next();
                        if (categoryName.equals("finish")) {
                            break;
                        }

                        System.out.print("Enter the name of the parent category: ");
                        String parentName = scn.next();

                        Category parent = ecommerceService.findCategory(root, parentName);
                        if (parent == null) {
                            System.out.println("Invalid Parent");
                            continue;
                        }


                        Category category = new Category(categoryName);
                        ecommerceService.addCategory(parent, category);
                    }

                    System.out.println("Final hierarchy:");
                    ecommerceService.displayHierarchy(root, 0);
                    break;

                case 2:
                    System.out.print("Enter the name of a category to find");
                    String categoryName = scn.next();
                    Category category = new Category(categoryName);
                    ecommerceService.getCategory(categoryName, lruCache, category);
                    break;

                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        } while (option != 3);
    }
}
