package views;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import utils.ReportHandler;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StatsView extends JPanel {
    private static final Logger logger = ReportHandler.getLogger();

    private List<PlayerStat> stats = new ArrayList<>();  // Kolekcja statystyk graczy

    // Panel with score labels
    private JPanel statsPanel;

    private JScrollPane scrollPane;

    // Panel with radio buttons for choosing category
    private JPanel categoryPanel;

    // Class representing a player's score
    private static class PlayerStat {
        String category;
        String nick;
        String result;

        PlayerStat(String category, String nick, String result) {
            this.category = category;
            this.nick = nick;
            this.result = result;
        }

        @Override
        public String toString() {
            return nick + ": " + result + " punktów";
        }
    }

    public StatsView() {
        setLayout(new BorderLayout());

        // Panel title
        JLabel title = new JLabel("Statystyki", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Get and add statistics to the stats list
        createStatLabels("Animals");
        createStatLabels("Geography");
        createStatLabels("History");
        createStatLabels("Maths");

        // Create panel for radio buttons
        createCategoryButtons();

        // Create panel for stats (empty at first)
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        add(statsPanel, BorderLayout.CENTER);

        // Create JScrollPane for scrolling through data
        scrollPane = new JScrollPane(statsPanel);
        scrollPane.setPreferredSize(new Dimension(600, 400));  // Can adjust dimensions
        // Ustaw nagłówek JScrollPane
        TitledBorder border = BorderFactory.createTitledBorder("Statistics");
        border.setTitleFont(new Font("Arial", Font.BOLD, 18)); // Set header font
        scrollPane.setBorder(border);
        // Change scroll speed of JScrollPane
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        // Add JScrollPane to frame
        add(scrollPane, BorderLayout.CENTER);

        // Go back button
        JButton backButton = new JButton("Powrót");
        backButton.addActionListener(e -> {
            setVisible(false);
            MenuView.goToMenu();  // Zastąp tym prawidłową metodą wywołania
        });
        add(backButton, BorderLayout.SOUTH);

        // Implicitly chooses Animals category
        selectDefaultCategory();
    }

    // Statistics from .txt file as PlayerStat objects
    private void createStatLabels(String category) {
        try {
            // Create a list of players' statistics
            List<PlayerStat> playerStats = readStats(category);

            // Traverse playerStats list and add results to stats
            for (PlayerStat stat : playerStats) {
                stats.add(stat);
            }
        } catch (Exception e) {
            logger.severe("Failed to load statistics for category: " + category);
            JOptionPane.showMessageDialog(this, "Failed to load statistics for " + category, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Create radio buttons for choosing category
    private void createCategoryButtons() {
        categoryPanel = new JPanel();

        // Create a group of radio buttons
        ButtonGroup group = new ButtonGroup();

        JRadioButton animalsButton = new JRadioButton("Zwierzeta");
        JRadioButton geographyButton = new JRadioButton("Geografia");
        JRadioButton historyButton = new JRadioButton("Historia");
        JRadioButton mathsButton = new JRadioButton("Matematyka");

        // Add radio buttons to group, so that only one can be chosen
        group.add(animalsButton);
        group.add(geographyButton);
        group.add(historyButton);
        group.add(mathsButton);

        // Add actions to buttons via lambda
        animalsButton.addActionListener(e -> showCategoryStats("Animals"));
        geographyButton.addActionListener(e -> showCategoryStats("Geography"));
        historyButton.addActionListener(e -> showCategoryStats("History"));
        mathsButton.addActionListener(e -> showCategoryStats("Maths"));

        // Add buttons to panel
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Układ poziomy dla przycisków
        categoryPanel.add(animalsButton);
        categoryPanel.add(geographyButton);
        categoryPanel.add(historyButton);
        categoryPanel.add(mathsButton);

        add(categoryPanel, BorderLayout.NORTH);  // Add panel with buttons under the header
    }

    // Method for showing scores for chosen category
    private void showCategoryStats(String category) {
        statsPanel.removeAll();  // Clear previous data

        // Filter statistics based on category via Stream
        List<PlayerStat> filteredStats = stats.stream()
                .filter(stat -> stat.category.equals(category))  // Filtrowanie statystyk według kategorii
                .collect(Collectors.toList());

        // Show statistics for chosen category
        displayCategoryStats(category, filteredStats);

        // Update panel
        statsPanel.revalidate();
        statsPanel.repaint();
    }

    // Show scores for given category
    private void displayCategoryStats(String category, List<PlayerStat> filteredStats) {
        // Change header title
        if (scrollPane.getBorder() instanceof TitledBorder titledBorder) {
            titledBorder.setTitle("Statystyki - " + category);
            scrollPane.repaint();
        }

        // Write user's actions to log
        logger.info("Użytkownik wybrał kategorię: " + category);
        logger.info("Wyświetlane statystyki: " + filteredStats);

        // Ads scores labels to panel
        for (PlayerStat stat : filteredStats) {
            statsPanel.add(new JLabel(stat.toString()));  // Tworzymy nową etykietę dla każdego wyniku
        }
    }

    // Method for setting the implicitly chosen category (e.g. "Animals")
    private void selectDefaultCategory() {
        // Get all radio buttons from categoryPanel
        for (Component comp : categoryPanel.getComponents()) {
            if (comp instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) comp;
                if (radioButton.getText().equals("Animals")) {
                    radioButton.setSelected(true);  // Ustawiamy "Animals" jako domyślnie wybraną kategorię
                    showCategoryStats("Animals");  // Wyświetlamy dane dla kategorii "Animals"
                    break;
                }
            }
        }
    }

    private static List<PlayerStat> readStats(String category) {
        String filePath = String.format("./stats/%s.txt", category);
        // Check if file exists
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return List.of(); // Return empty list
        }

        // Create a list to store stats (to return)
        List<PlayerStat> playerStats = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                // Check file syntax
                if (parts.length != 2) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }
                
                // Get stats data from file
                String nickname = parts[0];
                String result = parts[1];
                
                // Add the data to the list 
                PlayerStat stat = new PlayerStat(category, nickname, result);
                playerStats.add(stat);
            }
        } catch (IOException e) {
            logger.severe("I/O error occurred while loading users: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.severe("Unexpected error occurred when reading statistics data: " + e.getMessage());
            e.printStackTrace();
        }

        return playerStats;
    }
}
