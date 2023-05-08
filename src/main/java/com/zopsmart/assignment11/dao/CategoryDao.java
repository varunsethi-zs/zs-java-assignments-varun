package com.zopsmart.assignment11.dao;

import com.zopsmart.assignment11.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CategoryDao class to call crud operation function through jpa repository
 */
public interface CategoryDao extends JpaRepository<Category, Long> {

    /**
     * findByName function to find category based on categoryName
     */
    List<Category> findByName(String categoryName);
}
