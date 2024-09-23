package Servers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import sign_up_And_authentication.LogIn;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LogInHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Read the request body (JSON)
            byte[] body = exchange.getRequestBody().readAllBytes();
            String requestBody = new String(body, StandardCharsets.UTF_8);

            // Parse the JSON body
            JSONObject json = new JSONObject(requestBody);
            String email = json.getString("email");
            String password = json.getString("password");
            String code = json.getString("code");

            // Create a Login object and authenticate
            LogIn login = new LogIn(email, password, code);
            boolean success = login.authenticate();

            // Send the response
            String response = success ? "Login successful!" : "Login failed!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            // Return method not allowed if not POST
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
