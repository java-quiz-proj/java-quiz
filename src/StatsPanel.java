import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {
    private List<PlayerStat> stats = new ArrayList<>();  // Kolekcja statystyk graczy

    // Panel z etykietami wyników
    private JPanel statsPanel;

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

        // Zahardkodowane statystyki
        createStatLabels();

        // Tworzymy panel na radio buttony
        createCategoryButtons();

        // Tworzymy panel na statystyki (na razie pusty)
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        add(statsPanel, BorderLayout.CENTER);

        // Tworzymy JScrollPane dla przewijanych danych
        JScrollPane scrollPane = new JScrollPane(statsPanel);
        scrollPane.setPreferredSize(new Dimension(600, 400));  // Można dostosować rozmiar
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

    // Zahardkodowane statystyki w formie obiektów PlayerStat
    private void createStatLabels() {
        // Tworzymy listę statystyk graczy
        List<PlayerStat> playerStats = List.of(
                new PlayerStat("Zwierzęta", "nick11111", "1"),
                new PlayerStat("Zwierzęta", "nick2", "3"),
                new PlayerStat("Maths", "nick3", "4"),
                new PlayerStat("Zwierzęta", "nick3", "3"),
                new PlayerStat("Geografia", "nick4", "5"),
                new PlayerStat("Historia", "nick5", "6")
        );

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

        JRadioButton animalsButton = new JRadioButton("Zwierzęta");
        JRadioButton geographyButton = new JRadioButton("Geografia");
        JRadioButton historyButton = new JRadioButton("Historia");
        JRadioButton mathsButton = new JRadioButton("Matematyka");

        // Dodajemy radio buttony do grupy, by można było wybrać tylko jeden
        group.add(animalsButton);
        group.add(geographyButton);
        group.add(historyButton);
        group.add(mathsButton);

        // Dodajemy akcje do przycisków przy użyciu lambd
        animalsButton.addActionListener(e -> showCategoryStats("Zwierzęta"));
        geographyButton.addActionListener(e -> showCategoryStats("Geografia"));
        historyButton.addActionListener(e -> showCategoryStats("Historia"));
        mathsButton.addActionListener(e -> showCategoryStats("Matematyka"));

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
        JLabel categoryTitle = new JLabel(category + " - Statystyki");
        categoryTitle.setFont(new Font("Arial", Font.BOLD, 18));
        statsPanel.add(categoryTitle);  // Dodajemy tytuł kategorii

        // Dodajemy etykiety wyników do panelu
        for (PlayerStat stat : filteredStats) {
            statsPanel.add(new JLabel(stat.toString()));  // Tworzymy nową etykietę dla każdego wyniku
        }
    }

    // Funkcja do ustawienia domyślnie wybranej kategorii (np. "Zwierzęta")
    private void selectDefaultCategory() {
        // Pobieramy wszystkie radio buttony z panelu kategorii
        for (Component comp : categoryPanel.getComponents()) {
            if (comp instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) comp;
                if (radioButton.getText().equals("Zwierzęta")) {
                    radioButton.setSelected(true);  // Ustawiamy "Zwierzęta" jako domyślnie wybraną kategorię
                    showCategoryStats("Zwierzęta");  // Wyświetlamy dane dla kategorii "Zwierzęta"
                    break;
                }
            }
        }
    }

    private static void readStats(String category) {
        String filePath = String.format("./stats/%s.txt", category);

        // Check if file exists
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        List<PlayerStat> playerStats = List.of();
        
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
    }
}
