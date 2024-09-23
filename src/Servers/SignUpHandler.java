package Servers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sign_up_And_authentication.Sign_Up;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
public class SignUpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Parse the request body (assuming JSON format)
            byte[] body = exchange.getRequestBody().readAllBytes();
            String requestBody = new String(body, StandardCharsets.UTF_8);

            // Extract email and password from JSON
            JSONObject json = new JSONObject(requestBody);
            String email = json.getString("email");
            String password = json.getString("password");

            // Sign up the user (you can reuse the Sign_Up class)
            Sign_Up newUser = new Sign_Up(email, password);
            newUser.add_new_account();

            // Send a response back
            String response = "User signed up successfully!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            // Method not allowed
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
        }
    }
}