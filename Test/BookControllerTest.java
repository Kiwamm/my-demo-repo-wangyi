import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    @BeforeEach
    void setUp() {
        BookController.initBooks();
    }

    @Test
    void testGetBook() {
        JSONObject result = BookController.getBook(1);
        assertNotNull(result);
        assertEquals("Java Basics", result.getString("title"));
    }

    @Test
    void testCreateBook() {
        BookInfo newBook = new BookInfo(3, "New Book", "John Author", "000123456");
        JSONObject result = BookController.createBook(newBook);
        assertNotNull(result);
        assertEquals("Book created successfully", result.getString("message"));
    }

    @Test
    void testUpdateBook() {
        BookInfo updatedBook = new BookInfo(1, "Updated Java Basics", "John Doe", "123456");
        JSONObject result = BookController.updateBook(1, updatedBook);
        assertNotNull(result);
        assertEquals("Updated Java Basics", result.getString("title"));
    }

    @Test
    void testDeleteBook() {
        JSONObject result = BookController.deleteBook(1);
        assertNotNull(result);
        assertEquals("Book deleted successfully", result.getString("message"));
    }

    @Test
    void testGetBookNotFound() {
        JSONObject result = BookController.getBook(999);
        assertNotNull(result);
        assertEquals("Book not found", result.getString("error"));
    }
}
