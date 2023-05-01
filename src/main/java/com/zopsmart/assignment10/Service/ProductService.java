package com.zopsmart.assignment10.Service;

import com.zopsmart.assignment10.Dao.ProductDao;
import com.zopsmart.assignment10.Model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    private final ProductDao productDao;
    public Product product;

    public ProductService(ProductDao productDao) {

        this.productDao = productDao;
    }

    /**
     * createProductTable function to call Dao createProduct table function
     */
    public void createProductTable() throws SQLException {
        productDao.createProductTable();
    }

    /**
     * getAllProducts function to call Dao findAll function
     */
    public List<Product> getAllProducts() throws SQLException {
        return productDao.findAll();
    }

    /**
     * getProductById function to call Dao findbyID function
     */

    public Product getProductById(int productId) throws SQLException {
        return productDao.findById(productId);
    }

    /**
     * saveProduct function to call Dao saveProduct function
     */

    public void saveProduct() throws SQLException {
        productDao.saveProduct();
    }

    /**
     * updateProduct function to call Dao updateProduct function
     */

    public void updateProduct() throws SQLException {
        productDao.updateProduct();
    }

    /**
     * deleteProductById function to call Dao deleteById function
     */

    public void deleteProductById(int productId) throws SQLException {
        productDao.deleteById(productId);
    }


    /**
     * doesProductExist function to call Dao exists function
     */
    public boolean doesProductExist(int productId) throws SQLException {
        return productDao.exists(productId);
    }

}
