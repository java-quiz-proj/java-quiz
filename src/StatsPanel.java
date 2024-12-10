import javax.swing.*;
import javax.swing.border.TitledBorder;

import report.ReportHandler;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {
    Logger logger = ReportHandler.getLogger();

    private List<PlayerStat> stats = new ArrayList<>();  // Kolekcja statystyk graczy

    // Panel z etykietami wyników
    private JPanel statsPanel;

    private JScrollPane scrollPane;

    // Panel z przyciskami lub radio buttonami do wyboru kategorii
    private JPanel categoryPanel;

    // Klasa reprezentująca wynik gracza
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

    public StatsPanel() {
        setLayout(new BorderLayout());

        // Tytuł panelu
        JLabel title = new JLabel("Statystyki", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Otrzymanie i wrzucenie statystyk do listy stats
        createStatLabels("Animals");
        createStatLabels("Geography");
        createStatLabels("History");
        createStatLabels("Maths");

        // Tworzymy panel na radio buttony
        createCategoryButtons();

        // Tworzymy panel na statystyki (na razie pusty)
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        add(statsPanel, BorderLayout.CENTER);

        // Tworzymy JScrollPane dla przewijanych danych
        scrollPane = new JScrollPane(statsPanel);
        scrollPane.setPreferredSize(new Dimension(600, 400));  // Można dostosować rozmiar
        // Ustaw nagłówek JScrollPane
        TitledBorder border = BorderFactory.createTitledBorder("Statistics");
        border.setTitleFont(new Font("Arial", Font.BOLD, 18)); // Ustaw czcionke nagłówka
        scrollPane.setBorder(border);
        // Zmień szybkość scrollowania JScrollPane
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        // Dodaj JScrollPane do frame
        add(scrollPane, BorderLayout.CENTER);

        // Przycisk Powrót
        JButton backButton = new JButton("Powrót");
        backButton.addActionListener(e -> {
            JPanel parent = (JPanel) getParent();
            setVisible(false);
            CategoryView.chooseCat();  // Zastąp tym prawidłową metodą wywołania
        });
        add(backButton, BorderLayout.SOUTH);

        // Domyślnie wybieramy "Zwierzęta"
        selectDefaultCategory();
    }

    // Statystyki z pliku .txt w formie obiektów PlayerStat
    private void createStatLabels(String category) {
        // Tworzymy listę statystyk graczy
        List<PlayerStat> playerStats = readStats(category);

        // Przechodzimy po liście playerStats i dodajemy do stats
        for (PlayerStat stat : playerStats) {
            stats.add(stat);
        }
    }

    // Tworzymy przyciski (lub radio buttony) do wyboru kategorii
    private void createCategoryButtons() {
        categoryPanel = new JPanel();

        // Tworzymy grupę radio buttonów
        ButtonGroup group = new ButtonGroup();

        JRadioButton animalsButton = new JRadioButton("Animals");
        JRadioButton geographyButton = new JRadioButton("Geography");
        JRadioButton historyButton = new JRadioButton("History");
        JRadioButton mathsButton = new JRadioButton("Maths");

        // Dodajemy radio buttony do grupy, by można było wybrać tylko jeden
        group.add(animalsButton);
        group.add(geographyButton);
        group.add(historyButton);
        group.add(mathsButton);

        // Dodajemy akcje do przycisków przy użyciu lambd
        animalsButton.addActionListener(e -> showCategoryStats("Animals"));
        geographyButton.addActionListener(e -> showCategoryStats("Geography"));
        historyButton.addActionListener(e -> showCategoryStats("History"));
        mathsButton.addActionListener(e -> showCategoryStats("Maths"));

        // Dodajemy przyciski do panelu
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Układ poziomy dla przycisków
        categoryPanel.add(animalsButton);
        categoryPanel.add(geographyButton);
        categoryPanel.add(historyButton);
        categoryPanel.add(mathsButton);

        add(categoryPanel, BorderLayout.NORTH);  // Dodajemy panel z przyciskami tuż pod tytułem
    }

    // Funkcja do wyświetlania wyników z wybranej kategorii
    private void showCategoryStats(String category) {
        statsPanel.removeAll();  // Usuwamy poprzednie dane

        // Filtrowanie statystyk na podstawie kategorii za pomocą Stream
        List<PlayerStat> filteredStats = stats.stream()
                .filter(stat -> stat.category.equals(category))  // Filtrowanie statystyk według kategorii
                .collect(Collectors.toList());

        // Wyświetlanie statystyk dla wybranej kategorii
        displayCategoryStats(category, filteredStats);

        // Odświeżamy panel
        statsPanel.revalidate();
        statsPanel.repaint();
    }

    // Wyświetlanie wyników dla danej kategorii
    private void displayCategoryStats(String category, List<PlayerStat> filteredStats) {
        // Zmień tytuł nagłówka
        if (scrollPane.getBorder() instanceof TitledBorder titledBorder) {
            titledBorder.setTitle("Statystyki - " + category);
            scrollPane.repaint();
        }

        // Wpisz w log działanie użytkownika
        logger.info("Użytkownik wybrał kategorię: " + category);
        logger.info("Wyświetlane statystyki: " + filteredStats);

        // Dodajemy etykiety wyników do panelu
        for (PlayerStat stat : filteredStats) {
            statsPanel.add(new JLabel(stat.toString()));  // Tworzymy nową etykietę dla każdego wyniku
        }
    }

    // Funkcja do ustawienia domyślnie wybranej kategorii (np. "Animals")
    private void selectDefaultCategory() {
        // Pobieramy wszystkie radio buttony z panelu kategorii
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
            e.printStackTrace();
        }

        return playerStats;
    }
}
