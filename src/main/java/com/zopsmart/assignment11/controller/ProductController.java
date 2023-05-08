package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Product;
import com.zopsmart.assignment11.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ProductController class to call service layer function of products
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);


    /**
     * getAllProducts to retrieve all products
     */
    @Operation(summary = "Get all products", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
            @ApiResponse(responseCode = "404", description = "No products found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            HttpHeaders headers = new HttpHeaders();
            if (products == null) {
                headers.add("Custom-Header", "Product Not found");
                return ResponseEntity.notFound().headers(headers).build();
            }
            headers.add("Custom Header", "Product Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(products);
        } catch (Exception e) {
            LOGGER.error("Error getting all products: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * getProductById function to get product based on id
     */

    @Operation(summary = "Get a product by its id", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product with supplied id"),
            @ApiResponse(responseCode = "404", description = "Product not found with given id"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            HttpHeaders headers = new HttpHeaders();
            if (product.isEmpty()) {
                headers.add("Custom-Header", "Product not found");
                return ResponseEntity.notFound().headers(headers).build();
            }
            headers.add("Custom Header", "Product Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(product);
        } catch (Exception e) {
            LOGGER.error("Error getting product with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * getProductsByCategory function to retrieve products based on categoryName
     */

    @Operation(summary = "Get products by category", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products with given category name"),
            @ApiResponse(responseCode = "404", description = "No products found for the category"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoryName) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            HttpHeaders headers = new HttpHeaders();
            if (products == null) {
                headers.add("Custom-Header", "Products not found");
                return ResponseEntity.notFound().headers(headers).build();
            }
            headers.add("Custom Header", "Product Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(products);
        } catch (Exception e) {
            LOGGER.error("Error getting products by category {}: {}", categoryName, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * postProduct function to create new product
     */

    @Operation(summary = "Add a new product", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/post")
    public ResponseEntity<Object> postProduct(@RequestBody Product product) {
        try {
            Product addedProduct = productService.createProduct(product);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "Product added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(addedProduct);
        } catch (IllegalArgumentException e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(e.getMessage());
        }
    }

    /**
     * updateProduct function to update a product based on id
     */
    @Operation(summary = "Update a product by ID", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully with given id"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            HttpHeaders headers = new HttpHeaders();
            if (updatedProduct == null) {
                headers.add("Custom-Header", "Product not found");
                return ResponseEntity.notFound().headers(headers).build();
            }
            headers.add("Custom Header", "Product Updated Successfully");
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(updatedProduct);
        } catch (Exception e) {
            LOGGER.error("Error updating product with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * deleteProduct function to delete a product based on id provided
     */
    @Operation(summary = "Delete a product by ID", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully with supplied id"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product with id " + productId + " deleted successfully");
        } catch (Exception e) {
            LOGGER.error("Error deleting product with id {}: {}", productId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
