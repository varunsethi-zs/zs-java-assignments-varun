package com.zopsmart.assignment4.Model;

import java.util.*;

/**
 * LruCache class for Implementing Lru Structure
 */
public class LruCache {

public static int size;
    public LinkedHashMap<String, Category> cache;

    /**
     * LruCache parameterized constructor
     */

    public LruCache(int capacity) {
        size = capacity;
        this.cache = new LinkedHashMap<String, Category>(capacity);
    }
}
