package com.example.nrapesh.ecommerce;

/**
 * Stores information about a product.
 */
public class Product {

    private String name;
    private String description;
    private String retailer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public boolean isOutOfStock() {
        return isOutOfStock;
    }

    public void setIsOutOfStock(boolean isOutOfStock) {
        this.isOutOfStock = isOutOfStock;
    }

    private String category;
    private String color;
    private String url;
    private String imageUrl;
    private float price;
    private float discountPrice;
    private boolean isOutOfStock;

    // add image url, seller, brand, category.

    public Product(String description, float price) {
        this.description = description;
        this.price = price;
    }

    public Product(String name,
                   String description,
                   String retailer,
                   String category,
                   String color,
                   String url,
                   String imageUrl,
                   float price,
                   float discountPrice,
                   boolean isOutOfStock) {
        this.name = name;
        this.description = description;
        this.retailer = retailer;
        this.category = category;
        this.color = color;
        this.url = url;
        this.imageUrl = imageUrl;
        this.price = price;
        this.discountPrice = discountPrice;
        this.isOutOfStock = isOutOfStock;
    }
}
