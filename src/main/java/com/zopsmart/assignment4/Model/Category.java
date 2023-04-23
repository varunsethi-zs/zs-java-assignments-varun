package com.zopsmart.assignment4.Model;

import java.util.ArrayList;
import java.util.List;

/** Category class for implementing category structure*/
public class Category {
    public String name;
    public List<Category> subCategory;

    public Category(String name) {
        this.name = name;
        subCategory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Category> getSubCategory() {
        return subCategory;
    }

}
