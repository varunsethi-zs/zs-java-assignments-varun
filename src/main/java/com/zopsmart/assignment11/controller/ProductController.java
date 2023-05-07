package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Product;
import com.zopsmart.assignment11.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Api(value = "Product Management System", description = "Operations pertaining to products in Product Management System")
public class ProductController {

    @Autowired
    private ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);


    @ApiOperation(value = "View a list of available products", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved products"),
            @ApiResponse(code = 404, message = "No products found"),
            @ApiResponse(code = 500, message = "Internal Server Error")

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

    @ApiOperation(value = "Get a product by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved product"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
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

    @ApiOperation(value = "Get products by category", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved products"),
            @ApiResponse(code = 404, message = "No products found for the category"),
            @ApiResponse(code = 500, message = "Internal Server Error")
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

    @ApiOperation(value = "Add a new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product created successfully"),
            @ApiResponse(code = 500, message = "Internal Server Error")
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

    @ApiOperation(value = "Update a product by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product updated successfully"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal server error")
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


    @ApiOperation(value = "Delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product deleted successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product with id " + productId + " deleted successfully");
        } catch (Exception e) {
            LOGGER.error("Error deleting product with id {}: {}", productId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
