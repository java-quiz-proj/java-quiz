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
    
        public List<Integer> getScores(String category) {
            return categoryScores.getOrDefault(category, new ArrayList<>());
        }

        public double getAverageScore(String category) {
            List<Integer> scores = categoryScores.get(category);
            if(scores.isEmpty()) return 0.0;
            double sum = 0;
            for (int score : scores) {
                sum += score;
            }
            return sum / scores.size();
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

        // Dla debugu
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
            
                    // Jeżeli w pliku jest tylko jeden User (cast User na Liste wtedy wyrzuca wyjątek)
                    if (obj instanceof User) {
                        users.add((User) obj);
                    }
                    // Jeżeli w pliku jest wiele User
                    else if (obj instanceof List<?>) {
                        users = (List<User>) obj;
                    } 
                    else {
                        logger.warning("Unexpected data in the file.");
                    }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            
        }
    }

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

    // Uwierzytelnij użytkownika
    public boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                CurrentUser.getInstance().setCurrentUser(user, this);
                return true;
            }
        }
        return false;
    }

    public void updateUserScore(String category, int score) {
        String username = CurrentUser.getInstance().getUser().username;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.getStatistics().addScore(category, score); // Update score for the user
                saveUserScore(); // Save updated users to file after modification
                return;
            }
        }
        Logger logger = ReportHandler.getLogger();
        logger.warning("User not found: " + username);
    }

    // wpisz wynik użytkownika do pliku
    private void saveUserScore() {
        Logger logger = ReportHandler.getLogger();
        try (FileOutputStream fileOut = new FileOutputStream(this.filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(users);
            logger.info("User scores have been serialized.");
        } catch (IOException e) {
            logger.severe("Error when writing user scores" + e.getMessage());
            e.printStackTrace();
        }
    }
}