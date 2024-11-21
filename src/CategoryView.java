import javax.swing.*;

import java.awt.*;

public class CategoryView extends JPanel {
    private static JButton animals, maths, history, geography, logout, stats;
    private static JPanel buttonPanel;
    private static JLabel currUser;

    public CategoryView(){
        currUser = new JLabel("Zalogowany/a jako: " + CurrentUser.getInstance().getUser().getUsername());
        buttonPanel = new JPanel(new GridLayout(7, 1, 50, 15));
        animals = new JButton("Zwierzęta");
        maths = new JButton("Matematyka");
        history = new JButton("Historia");
        geography = new JButton("Geografia");
        stats = new JButton("Stats");
        stats.setBackground(new Color(100, 149, 237));
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

        animals.addActionListener(e -> addNewPanel("Animals"));
        maths.addActionListener(e -> addNewPanel("Maths"));
        history.addActionListener(e -> addNewPanel("History"));
        geography.addActionListener(e -> addNewPanel("Geography"));

        stats.addActionListener(e->showStats());

        logout.addActionListener(e -> {
            // logout user
            CurrentUser.getInstance().setCurrentUser(null, null);
            // set view to starting panel
            buttonPanel.setVisible(false);
            LoginView loginView = new LoginView();
            add(loginView);
            revalidate();
            repaint();
        });
    }

    private void addNewPanel(String cat){
        MyPanel panel = new MyPanel(cat);
        buttonPanel.setVisible(false);
        add(panel);
    }

    private void showStats() {
        StatsPanel statsPanel = new StatsPanel();
        buttonPanel.setVisible(false);
        add(statsPanel);
    }


    public static void chooseCat(){
        buttonPanel.setVisible(true);
    }
}
