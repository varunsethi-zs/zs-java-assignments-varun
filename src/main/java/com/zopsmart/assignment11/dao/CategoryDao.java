package com.zopsmart.assignment11.dao;

import com.zopsmart.assignment11.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long> {
    List<Category> findByName(String categoryName);
}
