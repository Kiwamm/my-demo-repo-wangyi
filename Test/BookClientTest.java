import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BookClientTest {

    @Test
    void testGetBook() throws Exception {
        BookClient.sendRequest("GET", "http://localhost:8000/book/1");
    }

    @Test
    void testCreateBook() throws Exception {
        String jsonRequest = "{\"id\": 3, \"title\": \"New Book\", \"author\": \"John Author\", \"isbn\": \"000123456\"}";
        BookClient.sendRequest("POST", "http://localhost:8000/book", jsonRequest);
    }

    @Test
    void testUpdateBook() throws Exception {
        String jsonRequest = "{\"id\": 3, \"title\": \"Updated Book\", \"author\": \"John Author\", \"isbn\": \"000123457\"}";
        BookClient.sendRequest("PUT", "http://localhost:8000/book", jsonRequest);
    }

    @Test
    void testDeleteBook() throws Exception {
        BookClient.sendRequest("DELETE", "http://localhost:8000/book/3");
    }
}
