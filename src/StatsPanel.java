import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {
    public StatsPanel() {
        setLayout(new GridLayout(3, 1, 10, 10)); // Prosty układ siatki

        JLabel title = new JLabel("Statystyki", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        // Przykładowe statystyki
        JLabel statsLabel1 = new JLabel("Zdobyte punkty: 120", SwingConstants.CENTER);
        JLabel statsLabel2 = new JLabel("Czas gry: 45 minut", SwingConstants.CENTER);
        JLabel statsLabel3 = new JLabel("Najlepszy wynik: 98%", SwingConstants.CENTER);

        add(statsLabel1);
        add(statsLabel2);
        add(statsLabel3);
    }
}
