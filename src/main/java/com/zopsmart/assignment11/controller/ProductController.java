package com.zopsmart.assignment11.controller;

import com.zopsmart.assignment11.entity.Product;
import com.zopsmart.assignment11.exception.ResourceNotFoundException;
import com.zopsmart.assignment11.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

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

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        HttpHeaders headers = new HttpHeaders();
        if (product.isEmpty()) {
            headers.add("Custom-Header", "Product not found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom Header", "Product Found Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(product);
    }


    @GetMapping("/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoryName) {
        List<Product> products = productService.getProductsByCategory(categoryName);
        HttpHeaders headers = new HttpHeaders();
        if (products == null) {
            headers.add("Custom-Header", "Products not found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom Header", "Product Found Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(products);
    }

    @PostMapping("/post")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        HttpHeaders headers = new HttpHeaders();
        if (createdProduct == null) {
            headers.add("Custom-Header", "Product not found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom Header", "Product Added Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        HttpHeaders headers = new HttpHeaders();
        if (updatedProduct == null) {
            headers.add("Custom-Header", "Product not found");
            return ResponseEntity.notFound().headers(headers).build();
        }
        headers.add("Custom Header", "Product Updated Successfully");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) throws ResourceNotFoundException {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product with id " + productId + " deleted successfully");
    }
}
