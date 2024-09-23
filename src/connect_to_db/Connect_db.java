package connect_to_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect_db {
    private Connection connection;
    private String jdbcUrl = "jdbc:postgresql://localhost:5433/authentication";
    private String username = "postgres";
    private String password = "admin1234";

    public Connect_db() throws SQLException, ClassNotFoundException {
        // Load the PostgreSQL driver
        Class.forName("org.postgresql.Driver");

        // Establish the database connection
        this.connection = DriverManager.getConnection(jdbcUrl, username, password);
    }

    // Method to get the connection
    public Connection getConnection() {
        return this.connection;
    }

    // Close the connection
    public void closeConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }
}
