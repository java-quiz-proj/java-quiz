package utils;

import models.*;

import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


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

        // For debug
        @Override
        public String toString() {
            return "User{" +
               "username='" + username + '\'' +
               ", password='" + password +
               '}';
        }
    }

    private List<User> users;
    private String filePath;
    private Logger logger;

    public LoginHandler(String filePath) {
        users = new ArrayList<>();
        this.filePath = filePath;

        logger = ReportHandler.getLogger();
        logger.info("Initializing LoginHandler with file path: " + filePath);
        loadUsers();
    }

    @SuppressWarnings("unchecked")
    public void loadUsers() {
        File file = new File(this.filePath);
        if (!file.exists()) {
            logger.warning("No users file found");
        }
        else if (file.length() > 0) {
            logger.info("Users file found. Loading users...");
            try (FileInputStream fileIn = new FileInputStream(this.filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {

                    Object obj = in.readObject();
            
                    // If there is only one User in the file (cast User onto the List throws exception)
                    if (obj instanceof User) {
                        users.add((User) obj);
                        logger.info("Single user loaded: " + obj);
                    }
                    // If there are many users in the file
                    else if (obj instanceof List<?>) {
                        users = (List<User>) obj;
                        logger.info("Multiple users loaded. Total users: " + users.size());
                    } 
                    else {
                        logger.warning("Unexpected data in the file.");
                    }

            } catch (IOException e) {
                logger.severe("I/O error occurred while loading users: " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                logger.severe("Class not found error occurred while loading users: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Add new user
    public void addUser(String username, String password) {
        logger.info("Attempting to add user: " + username);
        User user = new User(username, password);
        users.add(user); // add user to the list

        try (FileOutputStream fileOut = new FileOutputStream(this.filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

                // Serialize users
                out.writeObject(user);
                logger.info("User has been serialized: " + user);

        } catch (IOException e) {
            logger.severe("Error when serializing user: " + user);
            e.printStackTrace();
        } catch (Exception e) {
            logger.severe("Unexpected error occurred when adding user to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean doesUserExist(String username) {
        logger.info("Checking if user exists: " + username);
        try {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    logger.info("User found: " + username);
                    return true;
                }
            }
            logger.info("User not found: " + username);
        } catch (Exception e) {
            ReportHandler.getLogger().severe("Error checking user existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Authenticate user
    public boolean authenticate(String username, String password) {
        logger.info("Authenticating user: " + username);
        try {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    logger.info("Authentication successful for user: " + username);
                    CurrentUser.getInstance().setCurrentUser(user);
                    return true;
                }
            }
            logger.warning("Authentication failed for user: " + username);
        } catch (Exception e) {
            ReportHandler.getLogger().severe("Error during authentication: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}