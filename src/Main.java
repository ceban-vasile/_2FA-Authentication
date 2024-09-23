import connect_to_db.Connect_db;
import sign_up.Sign_Up;

public class Main {
    public static void main(String[] args) {
        // Initialize the Sign_up object
        Sign_Up dbAddNewUser = new Sign_Up("ceban.vasea20@gmail.com","vasile");
        dbAddNewUser.add_new_account();
    }
}
