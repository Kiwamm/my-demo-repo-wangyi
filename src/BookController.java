import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookController {

    private static final Map<Integer, BookInfo> books = new HashMap<>();

    public static void initBooks() {
        books.put(1, new BookInfo(1, "Java Basics", "John Doe", "12345"));
        books.put(2, new BookInfo(2, "Advanced Java", "Jane Smith", "67890"));
    }

    public static JSONObject getBook(int id) {
        BookInfo book = books.get(id);
        if (book != null) {
            return new JSONObject(book);
        } else {
            return new JSONObject().put("error", "Book not found");
        }
    }

    public static JSONObject createBook(BookInfo book) {
        books.put(book.getId(), book);
        return new JSONObject().put("message", "Book created successfully");
    }

    public static JSONObject updateBook(int id, BookInfo book) {
        if (books.containsKey(id)) {
            books.put(id, book);
            return new JSONObject().put("message", "Book updated successfully");
        } else {
            return new JSONObject().put("error", "Book not found");
        }
    }

    public static JSONObject deleteBook(int id) {
        if (books.containsKey(id)) {
            books.remove(id);
            return new JSONObject().put("message", "Book deleted successfully");
        } else {
            return new JSONObject().put("error", "Book not found");
        }
    }
}
