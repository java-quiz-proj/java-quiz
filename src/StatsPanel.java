import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StatsPanel extends JPanel {
    private List<JLabel> animalsLabels = new ArrayList<>();
    private List<JLabel> geographyLabels = new ArrayList<>();
    private List<JLabel> historyLabels = new ArrayList<>();
    private List<JLabel> mathsLabels = new ArrayList<>();

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
        hardcodeStats();

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
    private void hardcodeStats() {
        List<PlayerStat> stats = new ArrayList<>();
        stats.add(new PlayerStat("Zwierzęta", "nick1", "1"));
        stats.add(new PlayerStat("Zwierzęta", "nick2", "3"));
        stats.add(new PlayerStat("Maths", "nick3", "4"));
        stats.add(new PlayerStat("Zwierzęta", "nick3", "3"));
        stats.add(new PlayerStat("Geografia", "nick4", "5"));
        stats.add(new PlayerStat("Historia", "nick5", "6"));

        // Dodawanie do odpowiednich kategorii
        for (PlayerStat stat : stats) {
            switch (stat.category) {
                case "Zwierzęta":
                    animalsLabels.add(new JLabel(stat.toString()));
                    break;
                case "Geografia":
                    geographyLabels.add(new JLabel(stat.toString()));
                    break;
                case "Historia":
                    historyLabels.add(new JLabel(stat.toString()));
                    break;
                case "Maths":
                    mathsLabels.add(new JLabel(stat.toString()));
                    break;
            }
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

        // Dodajemy akcje do przycisków
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

        // Wyświetlanie statystyk dla wybranej kategorii
        switch (category) {
            case "Zwierzęta":
                displayCategoryStats(category, animalsLabels);
                break;
            case "Geografia":
                displayCategoryStats(category, geographyLabels);
                break;
            case "Historia":
                displayCategoryStats(category, historyLabels);
                break;
            case "Matematyka":
                displayCategoryStats(category, mathsLabels);
                break;
        }

        // Odświeżamy panel
        statsPanel.revalidate();
        statsPanel.repaint();
    }

    // Wyświetlanie wyników dla danej kategorii
    private void displayCategoryStats(String category, List<JLabel> labels) {
        JLabel categoryTitle = new JLabel(category + " - Statystyki");
        categoryTitle.setFont(new Font("Arial", Font.BOLD, 18));
        statsPanel.add(categoryTitle);  // Dodajemy tytuł kategorii

        // Dodajemy etykiety wyników do panelu
        for (JLabel label : labels) {
            statsPanel.add(label);
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
}
