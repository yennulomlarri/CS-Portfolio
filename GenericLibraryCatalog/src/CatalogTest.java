/**
 * Test class to demonstrate that the generic Catalog
 * works with different types of LibraryItem objects.
 */
public class CatalogTest {

    public static void main(String[] args) {

        System.out.println("===== GENERIC CATALOG TEST =====");

        // Catalog for Books
        Catalog<LibraryItem<String>> bookCatalog =
                new Catalog<>("Book Catalog");

        LibraryItem<String> book1 =
                new LibraryItem<>("B101", "Java Programming", "John Smith", "Book");

        LibraryItem<String> book2 =
                new LibraryItem<>("B102", "Data Structures", "Alice Brown", "Book");

        bookCatalog.addItem(book1);
        bookCatalog.addItem(book2);

        bookCatalog.displayCatalog();


        System.out.println("\n===============================");


        // Catalog for DVDs
        Catalog<LibraryItem<Integer>> dvdCatalog =
                new Catalog<>("DVD Catalog");

        LibraryItem<Integer> dvd1 =
                new LibraryItem<>(201, "Avengers Movie", "Marvel Studios", "DVD");

        LibraryItem<Integer> dvd2 =
                new LibraryItem<>(202, "Inception", "Christopher Nolan", "DVD");

        dvdCatalog.addItem(dvd1);
        dvdCatalog.addItem(dvd2);

        dvdCatalog.displayCatalog();


        System.out.println("\n===============================");


        // Remove item test
        System.out.println("Removing Book B101...");
        bookCatalog.removeItem("B101");

        bookCatalog.displayCatalog();
    }
}