/**
 * Generic Library Item class.
 * Represents any type of item stored in the library.
 *
 * @param <T> Type of Item ID
 */
public class LibraryItem<T> {

    private final T itemID;
    private final String title;
    private final String author;
    private final String itemType;

    public LibraryItem(T itemID, String title, String author, String itemType) {

        if (itemID == null)
            throw new IllegalArgumentException("Item ID cannot be null");

        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("Title cannot be empty");

        if (author == null || author.isEmpty())
            throw new IllegalArgumentException("Author cannot be empty");

        if (itemType == null || itemType.isEmpty())
            throw new IllegalArgumentException("Item type cannot be empty");

        this.itemID = itemID;
        this.title = title;
        this.author = author;
        this.itemType = itemType;
    }

    public T getItemID() {
        return itemID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getItemType() {
        return itemType;
    }

    public void displayItem() {
        System.out.println("Type: " + itemType);
        System.out.println("ID: " + itemID);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
    }
}