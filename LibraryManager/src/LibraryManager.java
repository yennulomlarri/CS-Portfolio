// Paste this code into LibraryManager.java
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManager {

    public static void main(String[] args) {
        HashMap<String, Book> bookCollection = new HashMap<>();
        Scanner userInput = new Scanner(System.in);

        while (true) {
            displayMenu();
            int userChoice;

            try {
                userChoice = userInput.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nError: Please enter a valid number.");
                userInput.nextLine();
                continue;
            }
            userInput.nextLine();

            switch (userChoice) {
                case 1:
                    addBook(bookCollection, userInput);
                    break;
                case 2:
                    borrowBook(bookCollection, userInput);
                    break;
                case 3:
                    returnBook(bookCollection, userInput);
                    break;
                case 4:
                    System.out.println("\nExiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("\nInvalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    public static void displayMenu() {
        System.out.println("\n--- Library System Menu ---");
        System.out.println("1. Add Books");
        System.out.println("2. Borrow Books");
        System.out.println("3. Return Books");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void addBook(HashMap<String, Book> collection, Scanner input) {
        System.out.print("Enter book title: ");
        String bookTitle = input.nextLine();

        if (collection.containsKey(bookTitle)) {
            System.out.print("Book already exists. Enter quantity to add: ");
            int quantityToAdd = input.nextInt();
            input.nextLine();

            Book existingBook = collection.get(bookTitle);
            existingBook.quantity += quantityToAdd;
            System.out.println("Quantity updated. New total: " + existingBook.quantity);
        } else {
            System.out.print("Enter author: ");
            String bookAuthor = input.nextLine();
            System.out.print("Enter initial quantity: ");
            int initialQuantity = input.nextInt();
            input.nextLine();

            Book newBook = new Book(bookTitle, bookAuthor, initialQuantity);
            collection.put(bookTitle, newBook);
            System.out.println("Book added successfully!");
        }
    }

    public static void borrowBook(HashMap<String, Book> collection, Scanner input) {
        System.out.print("Enter the title of the book to borrow: ");
        String titleToBorrow = input.nextLine();

        if (collection.containsKey(titleToBorrow)) {
            Book bookToBorrow = collection.get(titleToBorrow);

            System.out.print("Enter the number of copies to borrow: ");
            int quantityToBorrow = input.nextInt();
            input.nextLine();

            if (quantityToBorrow > 0 && quantityToBorrow <= bookToBorrow.quantity) {
                bookToBorrow.quantity -= quantityToBorrow;
                System.out.println("Success! You borrowed " + quantityToBorrow + " copie(s).");
                System.out.println("Remaining quantity for '" + bookToBorrow.title + "': " + bookToBorrow.quantity);
            } else {
                System.out.println("Error: Invalid number or not enough copies available.");
                System.out.println("Available quantity for '" + bookToBorrow.title + "': " + bookToBorrow.quantity);
            }
        } else {
            System.out.println("Error: Book not found in the library.");
        }
    }

    public static void returnBook(HashMap<String, Book> collection, Scanner input) {
        System.out.print("Enter the title of the book to return: ");
        String titleToReturn = input.nextLine();

        if (collection.containsKey(titleToReturn)) {
            Book bookToReturn = collection.get(titleToReturn);

            System.out.print("Enter the number of copies to return: ");
            int quantityToReturn = input.nextInt();
            input.nextLine();

            if (quantityToReturn > 0) {
                bookToReturn.quantity += quantityToReturn;
                System.out.println("Success! You returned " + quantityToReturn + " copie(s).");
                System.out.println("New total quantity for '" + bookToReturn.title + "': " + bookToReturn.quantity);
            } else {
                System.out.println("Error: Please enter a positive number to return.");
            }
        } else {
            System.out.println("Error: Book not found in the library. Cannot return a book that doesn't belong here.");
        }
    }
}
