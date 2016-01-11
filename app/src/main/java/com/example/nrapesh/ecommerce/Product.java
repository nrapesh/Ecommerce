package com.example.nrapesh.ecommerce;

/**
 * Stores information about a product.
 */

import android.graphics.Bitmap;

/**
 * @author VineetR
 *
 */

public class Product {

    private Integer id;

    private String name;
    private String brand;
    private String retailer;
    private float price;
    private float discountPrice;
    private String category;
    private String color;
    private String description;
    private String url;
    private String imageUrl;
    private Bitmap imageBitmap;
    private boolean isOutOfStock;

    public Product() {
        // TODO Auto-generated constructor stub
    }

    public Product(Integer id, String name, String brand, String retailer, float price, float discountedPrice, String category,
                   String color, String description, String url, String imageUrl, Bitmap imageBitmap, boolean isOutOfStock) {
        super();
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.retailer = retailer;
        this.price = price;
        this.discountPrice = discountedPrice;
        this.category = category;
        this.color = color;
        this.description = description;
        this.url = url;
        this.imageBitmap = imageBitmap;
        this.imageUrl = imageUrl;

        this.isOutOfStock = isOutOfStock;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean getIsOutOfStock() {
        return isOutOfStock;
    }

    public void setIsOutOfStock(boolean isOutOfStock) {
        this.isOutOfStock = isOutOfStock;
    }

    public boolean isOutOfStock() {
        return isOutOfStock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }


}