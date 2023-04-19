package com.zopsmart.assignment4.Service;

import com.zopsmart.assignment4.Model.Category;
import com.zopsmart.assignment4.Model.LruCache;

/**
 * EcommerceService Class for implementation of Display, Add and Get functions
 */
public class EcommerceService {
    LruCache lruCache = new LruCache(6);

    /**
     * getCategory Function to get the Category from memory
     */
    public void getCategory(String categoryName, Category category) {
        if (!lruCache.cache.containsKey(categoryName)) {

            if (!category.getName().contains(categoryName)) {
                System.out.println("Category Does not exists");
            } else {
                System.out.println(category.getName());
            }

        } else {

            Category categories = lruCache.cache.get(categoryName);
            lruCache.cache.remove(categoryName);
            lruCache.cache.put(categoryName, categories);
            System.out.println("Category exists");
        }
    }

    /**
     * addCategory Function to add a category
     */
    public void addCategory(Category category, Category child) {

        category.subCategory.add(child);
    }

    /**
     * displayHierarchy function to display class Hierarchy
     */

    public void displayHierarchy(Category category, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println("- " + category.getName());
        for (Category child : category.getSubCategory()) {
            displayHierarchy(child, level + 1);
        }
    }

    /**
     * findCategory function to find parent in Hierarchy
     */
    public Category findCategory(Category category, String name) {
        if (category.getName().equals(name)) {
            return category;
        }
        for (Category child : category.getSubCategory()) {
            Category result = findCategory(child, name);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
