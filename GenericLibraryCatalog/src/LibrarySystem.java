import java.util.Scanner;

/**
 * Generic Library  System
 * CS 1103 - Programming Assignment Unit 6

 * A command-line program that demonstrates the use of
 * Java generics to manage different types of library items.
 */
public class LibrarySystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Catalog<LibraryItem<String>> catalog =
            new Catalog<>("Main Library");

    public static void main(String[] args) {

        displayWelcomeMessage();

        int choice;

        do {
            displayMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1 -> addItem();
                case 2 -> removeItem();
                case 3 -> viewItem();
                case 4 -> catalog.displayCatalog();
                case 5 -> System.out.println("\nThank you for using the Library Catalog. Goodbye!");
                default -> System.out.println("Invalid choice. Please enter 1-5.");
            }

        } while (choice != 5);

        scanner.close();
    }

    private static void displayWelcomeMessage() {
        System.out.println("=====================================");
        System.out.println("      GENERIC LIBRARY CATALOG");
        System.out.println("=====================================");
        System.out.println("Manage Books, DVDs, and Magazines\n");
    }

    private static void displayMenu() {
        System.out.println("\n----- MAIN MENU -----");
        System.out.println("1. Add Item");
        System.out.println("2. Remove Item");
        System.out.println("3. View Item");
        System.out.println("4. Display Catalog");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static void addItem() {

        System.out.println("\nSelect Item Type:");
        System.out.println("1. Book");
        System.out.println("2. DVD");
        System.out.println("3. Magazine");
        System.out.print("Enter choice: ");

        int type = getUserChoice();

        String itemType;

        switch (type) {
            case 1 -> itemType = "Book";
            case 2 -> itemType = "DVD";
            case 3 -> itemType = "Magazine";
            default -> {
                System.out.println("Invalid type.");
                return;
            }
        }

        System.out.print("Enter Item ID: ");
        String id = scanner.nextLine();

        if (catalog.containsItem(id)) {
            System.out.println("Error: Item already exists.");
            return;
        }

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author/Creator: ");
        String author = scanner.nextLine();

        try {
            LibraryItem<String> item =
                    new LibraryItem<>(id, title, author, itemType);

            catalog.addItem(item);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeItem() {

        if (catalog.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }

        System.out.print("Enter Item ID to remove: ");
        String id = scanner.nextLine();

        catalog.removeItem(id);
    }

    private static void viewItem() {

        if (catalog.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }

        System.out.print("Enter Item ID: ");
        String id = scanner.nextLine();

        LibraryItem<String> item = catalog.getItem(id);

        if (item == null) {
            System.out.println("Item not found.");
        } else {
            System.out.println("\nItem Details:");
            item.displayItem();
        }
    }
}