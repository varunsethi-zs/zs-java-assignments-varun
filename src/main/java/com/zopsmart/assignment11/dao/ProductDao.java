package com.zopsmart.assignment11.dao;

import com.zopsmart.assignment11.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ProductDao class to call crud operations function through jpa repository
 */
public interface ProductDao extends JpaRepository<Product, Long> {

    /**
     * findCategoryByName function to retrieve products based on categoryName
     */
    List<Product> findByCategoryName(String categoryName);
}
