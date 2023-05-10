package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Product;
import com.zopsmart.assignment11.exception.BadRequestException;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
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
        List<Product> products = productService.getAllProducts();
        HttpHeaders headers = new HttpHeaders();
        if (products == null) {
            headers.add("Custom-Header", "Product Not found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom Header", "Product Found Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(products);
    }

    /**
     * getProductById function to get product based on id
     */

    @Operation(summary = "Get a product by its id", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product with supplied id"),
            @ApiResponse(responseCode = "404", description = "Product not found with given id"),
            @ApiResponse(responseCode = "400", description = "Invalid Id provided"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) throws BadRequestException {
        Optional<Product> product = productService.getProductById(id);
        HttpHeaders headers = new HttpHeaders();
        if (product.isEmpty()) {
            headers.add("Custom-Header", "Product not found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom Header", "Product Found Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(product);
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
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoryName) throws ResourceNotFoundException {
        List<Product> products = productService.getProductsByCategory(categoryName);
        HttpHeaders headers = new HttpHeaders();
        if (products == null) {
            headers.add("Custom-Header", "Products not found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom Header", "Product Found Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(products);
    }

    /**
     * postProduct function to create new product
     */

    @Operation(summary = "Add a new product", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Parameters Passed")
    })
    @PostMapping("/post")
    public ResponseEntity<Object> postProduct(@RequestBody Product product) throws BadRequestException {
        Product addedProduct = productService.createProduct(product);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "Product added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(addedProduct);
    }

    /**
     * updateProduct function to update a product based on id
     */
    @Operation(summary = "Update a product by ID", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully with given id"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Internal Id provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) throws BadRequestException, ResourceNotFoundException {
        Product updatedProduct = productService.updateProduct(id, product);
        HttpHeaders headers = new HttpHeaders();
        if (updatedProduct == null) {
            headers.add("Custom-Header", "Product not found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom Header", "Product Updated Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(updatedProduct);
    }


    /**
     * deleteProduct function to delete a product based on id provided
     */
    @Operation(summary = "Delete a product by ID", tags = "Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully with supplied id"),
            @ApiResponse(responseCode = "400", description = " Invalid Parameter Passed"),
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) throws BadRequestException, ResourceNotFoundException {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product with id " + productId + " deleted successfully");
    }
}
