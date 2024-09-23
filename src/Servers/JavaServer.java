package Servers;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class JavaServer {
    public JavaServer() throws IOException {
        // Create an HTTP server listening on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Define handlers for sign-up and login
        server.createContext("/signup", new SignUpHandler());
        server.createContext("/login", new LogInHandler());

        // Start the server
        server.setExecutor(null);
        server.start();
        System.out.println("Server is running on port 8000...");
    }
}
