package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final int customerID;
    private final String name;
    private final List<Product> shoppingCart;

    public Customer(int customerID, String name) {
        this.customerID = customerID;
        this.name = name;
        this.shoppingCart = new ArrayList<>();
    }

    public void addProductToCart(Product product) {
        if (product != null) {
            shoppingCart.add(product);
            System.out.println("Added to cart: " + product.getName());
        }
    }

    public void removeProductFromCart(Product product) {
        shoppingCart.remove(product);
        System.out.println("Removed from cart: " + product.getName());
    }

    public double calculateTotal() {
        double total = 0;
        for (Product p : shoppingCart) {
            total += p.getPrice();
        }
        return total;
    }

    public int getCustomerID() { return customerID; }
    public String getName() { return name; }
    public List<Product> getShoppingCart() { return new ArrayList<>(shoppingCart); }
}