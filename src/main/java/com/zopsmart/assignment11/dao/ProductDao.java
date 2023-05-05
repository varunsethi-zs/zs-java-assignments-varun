package com.zopsmart.assignment11.dao;

import com.zopsmart.assignment11.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String categoryName);
}
