import connect_to_db.Connect_db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize the Connect_db object
            Connect_db dbConnection = new Connect_db();

            // Get the connection object
            Connection connection = dbConnection.getConnection();

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            // Iterate through the result set
            while (resultSet.next()) {
                String columnValue = resultSet.getString("password");
                System.out.println("Column Value: " + columnValue);
            }

            // Close the connection
            dbConnection.closeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
