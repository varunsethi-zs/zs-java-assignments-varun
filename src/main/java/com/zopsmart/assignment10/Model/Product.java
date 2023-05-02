package com.zopsmart.assignment10.Model;



public class Product {

    private String name;
    private Integer id;
    private Double price;
    private String description;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    public Product(String name, Integer id, Double price, String description) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.description = description;
    }

    public Product(String name, String description, Double price) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
