package com.ecommerce.orders;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import java.util.List;

public class Order {
    private final int orderID;
    private final Customer customer;
    private final List<Product> products;
    private final double orderTotal;
    private String status;

    public Order(int orderID, Customer customer) {
        if (customer.getShoppingCart().isEmpty()) {
            throw new IllegalStateException("Cannot place order: Cart is empty.");
        }
        this.orderID = orderID;
        this.customer = customer;
        this.products = customer.getShoppingCart();
        this.orderTotal = customer.calculateTotal();
        this.status = "Processed";
    }

    public void generateOrderSummary() {
        System.out.println("\n--- FINAL ORDER SUMMARY ---");
        System.out.println("Order ID: " + orderID);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Items purchased:");
        for (Product p : products) {
            System.out.println(" - " + p.getName() + " ($" + p.getPrice() + ")");
        }
        System.out.printf("Total Amount: $%.2f\n", orderTotal);
        System.out.println("Order Status: " + status);
        System.out.println("---------------------------\n");
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Status Update: Order #" + orderID + " is now " + status);
    }
}