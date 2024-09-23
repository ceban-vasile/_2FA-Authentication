package sign_up;

import connect_to_db.Connect_db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sign_Up {
    private String email;
    private String password;

    public Sign_Up(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Method to hash the password
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Method to add a new account to the database
    public void add_new_account() {
        try {
            // Initialize the database connection
            Connect_db dbConnection = new Connect_db();
            Connection connection = dbConnection.getConnection();

            // Hash the password
            String hashedPassword = hashPassword(this.password);

            // Prepare SQL statement to insert new user
            String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set parameters for the prepared statement
            statement.setString(1, this.email);
            statement.setString(2, hashedPassword);

            // Execute the update
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user has been added successfully!");
            }

            // Close the database connection
            dbConnection.closeConnection();
        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

