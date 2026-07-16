import com.ecommerce.Customer;
import com.ecommerce.Product;
import com.ecommerce.orders.Order;
import java.util.ArrayList;
import java.util.List;
void main() {
    // 1. Browse Products (Initialization)
    List<Product> catalog = new ArrayList<>();
    Product laptop = new Product(101, "Laptop", 12000.00);
    Product phone = new Product(102, "Phone", 15000.00);

    catalog.add(laptop);
    catalog.add(phone);

    System.out.println("=== STORE CATALOG ===");
    for (Product p : catalog) {
        System.out.println(p.toString());
    }

    // --- CLEARING WARNINGS: Using all methods from Product ---
    laptop.setName("Gaming Laptop");  // Uses setName
    laptop.setPrice(1150.00);        // Uses setPrice
    laptop.setProductID(101);         // Uses setProductID

    System.out.println("\n[Manager Update]: " + laptop.getName() + " price updated to $" + laptop.getPrice());
    System.out.println("Product ID verified: " + laptop.getProductID());


    // 2. Customer Setup
    Customer user = new Customer(11, "Mateiyendou Kombat");
    System.out.println("\nLogging in: " + user.getName() + " (ID: " + user.getCustomerID() + ")");

    // 3. Add/Remove Logic (Clears 'unused' warnings in Customer)
    user.addProductToCart(laptop);
    user.addProductToCart(phone);
    user.removeProductFromCart(phone);

    // 4. Place Order with Error Handling
    try {
        Order firstOrder = new Order(5001, user);
        firstOrder.generateOrderSummary();
        firstOrder.updateStatus("Shipped");
    } catch (IllegalStateException e) {
        System.out.println("Error: " + e.getMessage());
    }

    // 5. Demonstrate Validation (Empty Cart)
    System.out.println("\nValidation Testing Check :");
    Customer guest = new Customer(22, "Guest");
    try {
        // This validates that the Order class correctly handles empty carts
        new Order(5002, guest);
    } catch (IllegalStateException e) {
        System.out.println("Validation Success: " + e.getMessage());
    }
}