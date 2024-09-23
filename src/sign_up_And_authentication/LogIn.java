package sign_up_And_authentication;

import connect_to_db.Connect_db;

import java.sql.*;

public class LogIn {
    private String email;
    private String password;
    private String code;  // 2FA code provided by the user

    public LogIn(String email, String password, String code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public boolean authenticate() {
        try {
            // Initialize the database connection
            Connect_db dbConnection = new Connect_db();
            Connection connection = dbConnection.getConnection();

            // Step 1: Validate email and password
            String sql = "SELECT password, secret FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String storedSecret = resultSet.getString("secret");

                if (password.equals(storedPassword)) {
                    // Step 2: Validate 2FA code using the stored secret
                    boolean is2FAValid = TwoFA.verifyCode(storedSecret, code);
                    if (is2FAValid) {
                        System.out.println("Login successful with 2FA!");
                        return true;
                    } else {
                        System.out.println("Invalid 2FA code!");
                    }
                } else {
                    System.out.println("Invalid password!");
                }
            } else {
                System.out.println("User not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
