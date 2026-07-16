package com.ecommerce;

public class Product {
    private int productID;
    private String name;
    private double price;

    public Product(int productID, String name, double price) {
        this.productID = productID;
        this.name = name;
        this.price = (price < 0) ? 0 : price;
    }

    // Getters
    public int getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    // Setters (These were causing warnings; now they will be used in Main)
    public void setProductID(int productID) { this.productID = productID; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = (price < 0) ? 0 : price; }

    @Override
    public String toString() {
        return String.format("ID: %d | Item: %-15s | Price: $%.2f", productID, name, price);
    }
}