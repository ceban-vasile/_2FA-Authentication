import Servers.JavaServer;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize the JavaServer to start listening for HTTP requests
            JavaServer objServer = new JavaServer(); // Correct instantiation
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
