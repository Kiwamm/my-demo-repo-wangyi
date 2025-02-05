import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class BookServer {

    public static void main(String[] args) throws Exception {
        // Initialize sample data
        BookController.initBooks();

        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/book", new BookHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

        System.out.println("Server started at http://localhost:8000/book");
    }

    static class BookHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            int statusCode = 200;

            String method = exchange.getRequestMethod();
            String uri = exchange.getRequestURI().toString();

            // Handle GET, POST, PUT, DELETE requests
            if (method.equals("GET") && uri.contains("/book")) {
                String idParam = uri.split("/")[2];
                int id = Integer.parseInt(idParam);
                response = BookController.getBook(id).toString();
            } else if (method.equals("POST")) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    requestBody.append(line);
                }
                JSONObject jsonRequest = new JSONObject(requestBody.toString());
                BookInfo book = new BookInfo(jsonRequest.getInt("id"), jsonRequest.getString("title"),
                        jsonRequest.getString("author"), jsonRequest.getString("isbn"));
                response = BookController.createBook(book).toString();
            } else if (method.equals("PUT")) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    requestBody.append(line);
                }
                JSONObject jsonRequest = new JSONObject(requestBody.toString());
                BookInfo book = new BookInfo(jsonRequest.getInt("id"), jsonRequest.getString("title"),
                        jsonRequest.getString("author"), jsonRequest.getString("isbn"));
                response = BookController.updateBook(book.getId(), book).toString();
            } else if (method.equals("DELETE")) {
                String idParam = uri.split("/")[2];
                int id = Integer.parseInt(idParam);
                response = BookController.deleteBook(id).toString();
            } else {
                statusCode = 405; // Method Not Allowed
                response = new JSONObject().put("error", "Invalid method").toString();
            }

            // Send response
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
