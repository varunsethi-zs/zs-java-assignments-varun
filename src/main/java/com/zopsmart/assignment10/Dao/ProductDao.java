package com.zopsmart.assignment10.Dao;

import com.zopsmart.assignment10.Model.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductDao {

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;
    private static final String FILEPATH = "/home/raramuri/Java/zs-java-assignments-varun/src/main/resources/postgresql.properties";

    /**
     * ProductDao constructor to get required parameters for connection from properties file
     */
    public ProductDao() {
        try (FileInputStream inputStream = new FileInputStream(FILEPATH)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            URL = properties.getProperty("database.url");
            USERNAME = properties.getProperty("database.username");
            PASSWORD = properties.getProperty("database.password");
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties file", e);
        }
    }

    /**
     * findAll function to retrieve all products fro database
     */
    public List<Product> findAll() {


        List<Product> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM product");

            ResultSet resultSet = statement.executeQuery();
            {
                while (resultSet.next()) {
                    products.add(createProductFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all products", e);
        }
        return products;
    }

    /**
     * findById function to retrieve products by ID
     */
    public Product findById(int id) {

        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE id = ?");
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createProductFromResultSet(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding product by id " + id, e);
        }
    }

    /**
     * saveProduct function to insert product entry in database
     */
    public void saveProduct(Product product) {
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO product (name,price,description) VALUES ( ?, ?,?)")) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error in inserting product ");
        }
    }

    /**
     * updateProduct function to update any product through Id
     */
    public void updateProduct(Product product, int id) {
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE product SET name = ?, description = ?, price = ? WHERE id = ?")) {
            statement.setString(1, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setString(2, product.getDescription());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error updating product ");
        }
    }

    /**
     * deleteById function to delete a product entry from database through ID
     */

    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product by id " + id, e);
        }
    }

    /**
     * exists function to check wheather a product exists
     */

    public boolean exists(int id) {
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM product WHERE id = ?");
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if product exists by id " + id);
        }
    }

    /**
     * createProductFromResultSet function to set product attributes with the help of resultSet
     */
    private Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        Double price = resultSet.getDouble("price");
        return new Product(name, id, price, description);
    }

    /**
     * createProductTable function to create a product Table in a database
     */
    public void createProductTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                URL, USERNAME, PASSWORD)) {
            String query = "CREATE TABLE IF NOT EXISTS product(id SERIAL ,name VARCHAR(70),price Decimal(10,2),description TEXT);";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
        }
    }
}
