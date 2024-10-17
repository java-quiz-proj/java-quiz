import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LoginHandler {

    public static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        // Dla debugu
        @Override
        public String toString() {
            return "username: " + username + " password: " + password;
        }
    }

    private List<User> users;

    // Zczytaj użytkowników z pliku txt
    public LoginHandler(String filePath) throws IOException {
        users = new ArrayList<>();
        loadUsers(filePath);
    }

    // Method to load users from file
    private void loadUsers(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                users.add(new User(parts[0].trim(), parts[1].trim()));
                for (User user : users) {
                    System.out.println(user);
                }
            }
        }
        reader.close();
    }

    // Uwierzytelnij użytkownika
    public boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // // Main method for login process
    // public static void main(String[] args) {
    //     try {
    //         LoginHandler LogInHandler = new LoginHandler("users.txt");

    //         // Simulate input for testing (replace with actual input as needed)
    //         String username = "john";  // hardcoded username
    //         String password = "pass123";  // hardcoded password

    //         if (LogInHandler.authenticate(username, password)) {
    //             System.out.println("Login successful!");
    //         } else {
    //             System.out.println("Invalid username or password.");
    //         }

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}