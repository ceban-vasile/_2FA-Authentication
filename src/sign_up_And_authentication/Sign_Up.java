package sign_up_And_authentication;

import connect_to_db.Connect_db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sign_Up {
    private String email;
    private String password;
    private String secret;

    public Sign_Up(String email, String password) {
        this.email = email;
        this.password = password;
        this.secret = TwoFA.generateSecret();
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

    // Method to check if the email already exists
    private boolean emailExists(Connection connection) throws SQLException {
        String sql = "SELECT email FROM users WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, this.email);
        ResultSet resultSet = statement.executeQuery();

        // If the result set has any rows, the email exists
        return resultSet.next();
    }

    // Method to add a new account to the database
    public void add_new_account() {
        try {
            // Initialize the database connection
            Connect_db dbConnection = new Connect_db();
            Connection connection = dbConnection.getConnection();

            // Check if the email already exists
            if (emailExists(connection)) {
                System.out.println("This email is already registered. Please use another email.");
            } else {
                // Hash the password
                String hashedPassword = hashPassword(this.password);

                // Prepare SQL statement to insert new user
                String sql = "INSERT INTO users (email, password, secret) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                // Set parameters for the prepared statement
                statement.setString(1, this.email);
                statement.setString(2, hashedPassword);
                statement.setString(3, secret);

                // Execute the update
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("A new user has been added successfully!");
                }
            }

            // Close the database connection
            dbConnection.closeConnection();
        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

