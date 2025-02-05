import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookClient {

    public static void main(String[] args) throws Exception {
        // Get a book by ID (GET request)
        System.out.println("Get book:");
        sendRequest("GET", "http://localhost:8000/book/1");

        // Create a book (POST request)
        String jsonRequest = "{\"id\": 3, \"title\": \"New Book\", \"author\": \"John Author\", \"isbn\": \"000123456\"}";
        System.out.println("Create book:");
        sendRequest("POST", "http://localhost:8000/book", jsonRequest);

        // Update a book (PUT request)
        jsonRequest = "{\"id\": 3, \"title\": \"Updated Book\", \"author\": \"John Author\", \"isbn\": \"000123457\"}";
        System.out.println("Update book:");
        sendRequest("PUT", "http://localhost:8000/book", jsonRequest);

        // Delete a book (DELETE request)
        System.out.println("Delete book:");
        sendRequest("DELETE", "http://localhost:8000/book/3");
    }

    public static void sendRequest(String method, String urlString) throws Exception {
        sendRequest(method, urlString, null);
    }

    public static void sendRequest(String method, String urlString, String jsonRequest) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        if (jsonRequest != null) {
            connection.setDoOutput(true);
            connection.getOutputStream().write(jsonRequest.getBytes());
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println(content.toString());
    }
}
