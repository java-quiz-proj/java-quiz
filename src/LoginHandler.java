import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoginHandler {

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;

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
    private String filePath;

    public LoginHandler(String filePath) {
        users = new ArrayList<>();
        this.filePath = filePath;
        loadUsers();
    }

    public void loadUsers() {
        File file = new File(this.filePath);
        if (!file.exists()) {
            System.err.println("No users file found");
        }
        else if (file.length() > 0) {
            try (FileInputStream fileIn = new FileInputStream(this.filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {

                    Object obj = in.readObject();
            
                    // Jeżeli w pliku jest tylko jeden User (cast User na Liste wtedy wyrzuca wyjątek)
                    if (obj instanceof User) {
                        users.add((User) obj);
                    }
                    // Jeżeli w pliku jest wiele User
                    else if (obj instanceof List<?>) {
                        users = (List<User>) obj;
                    } 
                    else {
                        System.err.println("Unexpected data in the file.");
                    }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            
        }
    }

    public void addUser(String username, String password) {
        User user = new User(username, password);
        try (FileOutputStream fileOut = new FileOutputStream(this.filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(user);
            users.add(user);
            System.out.println("User has been serialized: " + user);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}