import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {
    private JTable animalsStats;
    private JTable geographyStats;
    private JTable historyStats;
    private JTable mathsStats;

    public StatsPanel() {
        // Using BoxLayout for vertical arrangement
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Title label
        JLabel title = new JLabel("Statystyki", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        // Define the table data and column names
        String[][] rowData = {{"user1", "wynik1"}, {"user2", "wynik2"}};
        String[] columnNames = {"Użytkownik", "Najwyższy wynik"};

        // Initialize JTable instances
        animalsStats = new JTable(rowData, columnNames);
        geographyStats = new JTable(rowData, columnNames);
        historyStats = new JTable(rowData, columnNames);
        mathsStats = new JTable(rowData, columnNames);

        // Set a preferred size for the tables (optional but recommended)
        animalsStats.setPreferredScrollableViewportSize(new Dimension(400, 150));
        geographyStats.setPreferredScrollableViewportSize(new Dimension(400, 150));
        historyStats.setPreferredScrollableViewportSize(new Dimension(400, 150));
        mathsStats.setPreferredScrollableViewportSize(new Dimension(400, 150));

        // Add tables to JScrollPanes and add them to the panel
        add(new JScrollPane(animalsStats));
        add(new JScrollPane(geographyStats));
        add(new JScrollPane(historyStats));
        add(new JScrollPane(mathsStats));

        // Back button
        JButton backButton = new JButton("Powrót");
        backButton.addActionListener(e -> {
            JPanel parent = (JPanel) getParent();
            setVisible(false);
            CategoryView.chooseCat();  // Replace with actual method call
        });
        add(backButton);
    }

    private JTable loadAnimalsStats() {return new JTable();};
    private JTable loadGeographyStats() {return new JTable();};
    private JTable loadHistoryStats() {return new JTable();};
    private JTable loadMathsStats() {return new JTable();};
}
