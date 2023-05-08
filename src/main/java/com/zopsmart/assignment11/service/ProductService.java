package com.zopsmart.assignment11.service;

import com.zopsmart.assignment11.dao.CategoryDao;
import com.zopsmart.assignment11.dao.ProductDao;
import com.zopsmart.assignment11.entity.Category;
import com.zopsmart.assignment11.entity.Product;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ProductService class to implement Crud operation on Products
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productRepository;

    @Autowired
    private CategoryDao categoryDao;


    /**
     * getAllProducts function to retrieve all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * getProductById function to retrieve product based on id provided
     */

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * getProductsByCategory function to retrieve all products based on category
     */
    public List<Product> getProductsByCategory(String categoryName) throws ResourceNotFoundException {
        List<Category> categories = categoryDao.findByName(categoryName);
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Category does not exists");
        }
        return productRepository.findByCategoryName(categoryName);
    }

    /**
     * createProduct function to post new product
     */

    public Product createProduct(Product product) {
        String categoryName = product.getCategory().getName();
        String productName = product.getName();
        Double price = product.getPrice();
        if (categoryName == null || productName == null || price <= 0) {
            throw new IllegalArgumentException("Invalid parameters passed.");
        }
        List<Category> existingCategories = categoryDao.findByName(categoryName);
        if (!existingCategories.isEmpty()) {
            product.setCategory(existingCategories.get(0));
        } else {
            categoryDao.save(product.getCategory());
        }
        return productRepository.save(product);
    }

    /**
     * deleteProduct function to delete product based on id provided
     */
    public void deleteProduct(Long productId) throws ResourceNotFoundException {
        boolean exists = doesProductExists(productId);
        if (!exists) {
            throw new ResourceNotFoundException("product corresponding to :" + productId + "does not exits");
        }
        productRepository.deleteById(productId);
    }

    /**
     * updateProduct function to update a product based on id provided
     */

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            Category updatedCategory = updatedProduct.getCategory();
            Long categoryId = updatedCategory.getId();
            if (categoryId == null) {
                List<Category> existingCategories = categoryDao.findByName(updatedCategory.getName());
                Category existingCategory = null;
                if (!existingCategories.isEmpty()) {
                    existingCategory = existingCategories.get(0);
                }
                if (existingCategory == null) {
                    existingCategory = categoryDao.save(updatedCategory);
                }
                product.setCategory(existingCategory);
            } else {
                Category category = categoryDao.findById(categoryId).orElse(null);
                if (category == null) {
                    throw new IllegalArgumentException("Invalid category id: " + categoryId);
                }
                product.setCategory(category);
            }
            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Invalid product id: " + id);
        }
    }


    /**
     * doesProductExists function to check whether a product exits corresponding to a given id
     */
    public boolean doesProductExists(Long productId) {
        return productRepository.existsById(productId);
    }

}
