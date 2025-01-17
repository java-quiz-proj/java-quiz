package views;

import models.*;
import utils.ReportHandler;

import javax.swing.*;

import java.awt.*;

import java.util.logging.Logger;

public class MenuView extends JPanel {
    private static JButton animals, maths, history, geography, logout, stats;
    private static JPanel buttonPanel;
    private static JLabel currUser;
    private JFrame frame;
    private static final Logger logger = ReportHandler.getLogger();

    public MenuView(JFrame frame){
        this.frame = frame;

        try {
            // Add "Logged in as: " label
            currUser = new JLabel("Zalogowany/a jako: " + CurrentUser.getInstance().getUser().getUsername());
            buttonPanel = new JPanel(new GridLayout(7, 1, 50, 15));

            // Category buttons
            animals = new JButton("Zwierzęta");
            maths = new JButton("Matematyka");
            history = new JButton("Historia");
            geography = new JButton("Geografia");

            // Statistics button
            stats = new JButton("Statystyki");
            stats.setBackground(new Color(100, 149, 237));

            // Logout button
            logout = new JButton("Wyloguj");
            logout.setBackground(new Color(255, 69, 0));

            buttonPanel.add(currUser);
            buttonPanel.add(maths);
            buttonPanel.add(animals);
            buttonPanel.add(history);
            buttonPanel.add(geography);
            buttonPanel.add(stats);
            buttonPanel.add(logout);

            add(buttonPanel);
            logger.info("MenuView added to frame");

            animals.addActionListener(e -> goToQuiz("Animals"));
            maths.addActionListener(e -> goToQuiz("Maths"));
            history.addActionListener(e -> goToQuiz("History"));
            geography.addActionListener(e -> goToQuiz("Geography"));

            stats.addActionListener(e -> {
                try {
                    logger.info("User selected Stats");
                    showStats();
                } catch (Exception ex) {
                    logger.severe("Error showing stats: " + ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "An error occurred while showing stats. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            logout.addActionListener(e -> {
                try {
                    logger.info("User " + CurrentUser.getInstance().getUser().getUsername() + " logged out");
                    // Logout user
                    CurrentUser.getInstance().setCurrentUser(null);
                    // Set view to starting panel
                    buttonPanel.setVisible(false);
                    LoginView loginView = new LoginView(frame);
                    add(loginView);
                    revalidate();
                    repaint();
                } catch (Exception ex) {
                    logger.severe("Error during logout: " + ex.getMessage());
                    JOptionPane.showMessageDialog(frame, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception ex) {
            logger.severe("Error initializing MenuView: " + ex.getMessage());
            JOptionPane.showMessageDialog(frame, "An error occurred while initializing the menu. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Change to QuizView
    private void goToQuiz(String cat){
        try {
            logger.info("User selected " + cat + " category");
            QuizView panel = new QuizView(cat);
            buttonPanel.setVisible(false);
            add(panel);
        } catch (Exception ex) {
            logger.severe("Error navigating to " + cat + " quiz: " + ex.getMessage());
            JOptionPane.showMessageDialog(frame, "An error occurred while loading the quiz. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Change to StatsView
    private void showStats() {
        try {
            StatsView statsView = new StatsView();

            // Hide the button panel
            buttonPanel.setVisible(false);


            frame.add(statsView);
            statsView.setPreferredSize(new Dimension(600, 400));
            frame.revalidate();
            frame.repaint();
        } catch (Exception ex) {
            logger.severe("Error displaying stats: " + ex.getMessage());
            JOptionPane.showMessageDialog(frame, "An error occurred while displaying stats. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void goToMenu(){
        try {
            logger.info("User navigated back to MenuView");
            buttonPanel.setVisible(true);
        } catch (Exception ex) {
            logger.severe("Error navigating back to MenuView: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "An error occurred while returning to the menu. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
