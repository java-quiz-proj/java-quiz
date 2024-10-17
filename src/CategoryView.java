import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;

public class CategoryView extends JPanel {
    private static JButton animals, maths, history, geography;
    public CategoryView(){
        animals = new JButton("Zwierzęta");
        maths = new JButton("Matematyka");
        history = new JButton("Historia");
        geography = new JButton("Geografia");
        add(maths);
        add(animals);
        add(history);
        add(geography);

        animals.addActionListener(e -> {
            addNewPanel("animals");
        });

        maths.addActionListener(e -> {
            addNewPanel("maths");
        });

        history.addActionListener(e -> {
            addNewPanel("history");
        });

        geography.addActionListener(e -> {
            addNewPanel("geography");
        });

        //setLayout(new GridLayout(5, 1, 50, 15));  // Ustawienie GridLayout dla przycisków
    }
    private void addNewPanel(String cat){
        MyPanel panel = new MyPanel(cat);
        animals.setVisible(false);
        maths.setVisible(false);
        history.setVisible(false);
        geography.setVisible(false);
        add(panel);
    }

    public static void chooseCat(){
        animals.setVisible(true);
        maths.setVisible(true);
        history.setVisible(true);
        geography.setVisible(true);
    }
}
