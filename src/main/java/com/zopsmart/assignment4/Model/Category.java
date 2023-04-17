package com.zopsmart.assignment4.Model;

import java.util.ArrayList;
import java.util.List;

/** Category class for implementing category structure*/
public class Category {
    public String name;
    public List<Category> children;

    public Category(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Category> getChildren() {
        return children;
    }

}
