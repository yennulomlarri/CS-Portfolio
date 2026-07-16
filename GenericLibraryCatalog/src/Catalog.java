import java.util.ArrayList;

/**
 * Generic Catalog class for managing library items.
 * @param <T> Type of LibraryItem stored in catalog
 */
public class Catalog<T extends LibraryItem<?>> {

    private final ArrayList<T> items;
    private final String catalogName;

    public Catalog(String catalogName) {
        this.catalogName = catalogName;
        items = new ArrayList<>();
    }

    public void addItem(T item) {

        if (item == null) {
            System.out.println("Error: Cannot add null item.");
            return;
        }

        if (findItemById(item.getItemID()) != null) {
            System.out.println("Error: Item already exists.");
            return;
        }

        items.add(item);
        System.out.println("Item added successfully.");
    }

    public void removeItem(Object itemID) {

        T item = findItemById(itemID);

        if (item == null) {
            System.out.println("Error: Item not found.");
            return;
        }

        items.remove(item);
        System.out.println("Item removed successfully.");
    }

    public T getItem(Object itemID) {
        return findItemById(itemID);
    }

    public boolean containsItem(Object itemID) {
        return findItemById(itemID) != null;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void displayCatalog() {

        System.out.println("\n=== " + catalogName + " ===");

        if (items.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }

        for (T item : items) {
            System.out.println("------------------");
            item.displayItem();
        }
    }

    private T findItemById(Object itemID) {

        for (T item : items) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }

        return null;
    }
}