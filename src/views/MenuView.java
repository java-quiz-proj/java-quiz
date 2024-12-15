package views;

import models.*;

import javax.swing.*;

import java.awt.*;

public class MenuView extends JPanel {
    private static JButton animals, maths, history, geography, logout, stats;
    private static JPanel buttonPanel;
    private static JLabel currUser;
    private JFrame frame;

    public MenuView(JFrame frame){
        this.frame = frame;

        // Add "Logged in as: " label
        currUser = new JLabel("Zalogowany/a jako: " + CurrentUser.getInstance().getUser().getUsername());
        buttonPanel = new JPanel(new GridLayout(7, 1, 50, 15));

        // Category buttons
        animals = new JButton("Zwierzęta");
        maths = new JButton("Matematyka");
        history = new JButton("Historia");
        geography = new JButton("Geografia");

        // Statistics button
        stats = new JButton("Stats");
        stats.setBackground(new Color(100, 149, 237));

        // Logout button
        logout = new JButton("Logout");
        logout.setBackground(new Color(255, 69, 0));

        buttonPanel.add(currUser);
        buttonPanel.add(maths);
        buttonPanel.add(animals);
        buttonPanel.add(history);
        buttonPanel.add(geography);
        buttonPanel.add(stats);
        buttonPanel.add(logout);

        add(buttonPanel);

        animals.addActionListener(e -> goToQuiz("Animals"));
        maths.addActionListener(e -> goToQuiz("Maths"));
        history.addActionListener(e -> goToQuiz("History"));
        geography.addActionListener(e -> goToQuiz("Geography"));

        stats.addActionListener(e->showStats());

        logout.addActionListener(e -> {
            // Logout user
            CurrentUser.getInstance().setCurrentUser(null);
            // Set view to starting panel
            buttonPanel.setVisible(false);
            LoginView loginView = new LoginView(frame);
            add(loginView);
            revalidate();
            repaint();
        });
    }

    // Change to QuizView
    private void goToQuiz(String cat){
        QuizView panel = new QuizView(cat);
        buttonPanel.setVisible(false);
        add(panel);
    }

    // Change to StatsView
    private void showStats() {
        StatsView statsView = new StatsView();

        // Hide the button panel
        buttonPanel.setVisible(false);


        frame.add(statsView);
        statsView.setPreferredSize(new Dimension(600, 400));
        frame.revalidate();
        frame.repaint();
    }

    public static void goToMenu(){
        buttonPanel.setVisible(true);
    }
}
