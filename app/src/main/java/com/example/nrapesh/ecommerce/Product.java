package com.example.nrapesh.ecommerce;

/**
 * Stores information about a product.
 */
public class Product {

    private String description;
    private int price;

    public Product(String description, int price) {
        this.description = description;
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
