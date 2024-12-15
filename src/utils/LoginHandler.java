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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class LoginHandler {

    public static class Statistics implements Serializable {
        private static final long serialVersionUID = 1L;
    
        private Map<String, List<Integer>> categoryScores;
    
        public Statistics() {
            categoryScores = new HashMap<>();
            categoryScores.put("Animals", new ArrayList<>());
            categoryScores.put("Maths", new ArrayList<>());
            categoryScores.put("Geography", new ArrayList<>());
            categoryScores.put("History", new ArrayList<>());
        }
    
        public void addScore(String category, int score) {
            categoryScores.get(category).add(score);
        }

        @Override
        public String toString() {
            return "QuizStatistics{" + "categoryScores=" + categoryScores + '}';
        }
    }

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;

        private String username;
        private String password;
        private Statistics statistics;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.statistics = new Statistics();
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public Statistics getStatistics() {
            return statistics; // Provide access to quiz statistics
        }

        // For debug
        @Override
        public String toString() {
            return "User{" +
               "username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", statistics=" + statistics +
               '}';
        }
    }

    private List<User> users;
    private String filePath;

    public LoginHandler(String filePath) {
        users = new ArrayList<>();
        this.filePath = filePath;
        loadUsers();
    }

    @SuppressWarnings("unchecked")
    public void loadUsers() {
        File file = new File(this.filePath);
        Logger logger = ReportHandler.getLogger();
        if (!file.exists()) {
            logger.warning("No users file found");
        }
        else if (file.length() > 0) {
            try (FileInputStream fileIn = new FileInputStream(this.filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {

                    Object obj = in.readObject();
            
                    // If there is only one User in the file (cast User onto the List throws exception)
                    if (obj instanceof User) {
                        users.add((User) obj);
                    }
                    // If there are many users in the file
                    else if (obj instanceof List<?>) {
                        users = (List<User>) obj;
                    } 
                    else {
                        logger.warning("Unexpected data in the file.");
                    }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Add new user
    public void addUser(String username, String password) {
        Logger logger = ReportHandler.getLogger();
        User user = new User(username, password);
        try (FileOutputStream fileOut = new FileOutputStream(this.filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

                out.writeObject(user);
                users.add(user);
                logger.info("User has been serialized: " + user);

        } catch (IOException e) {
            logger.severe("Error when serializing user: " + user);
            e.printStackTrace();
        }
    }

    public boolean doesUserExist(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Authenticate user
    public boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                CurrentUser.getInstance().setCurrentUser(user, this);
                return true;
            }
        }
        return false;
    }
}