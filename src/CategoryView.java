import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;

public class CategoryView extends JPanel {
    private static JButton animals, maths, history, geography;
    private static JPanel buttonPanel;

    public CategoryView(){
        buttonPanel = new JPanel(new GridLayout(4, 1, 50, 15));
        animals = new JButton("Zwierzęta");
        maths = new JButton("Matematyka");
        history = new JButton("Historia");
        geography = new JButton("Geografia");

        buttonPanel.add(maths);
        buttonPanel.add(animals);
        buttonPanel.add(history);
        buttonPanel.add(geography);

        add(buttonPanel);

        animals.addActionListener(e -> addNewPanel("animals"));
        maths.addActionListener(e -> addNewPanel("maths"));
        history.addActionListener(e -> addNewPanel("history"));
        geography.addActionListener(e -> addNewPanel("geography"));
    }

    private void addNewPanel(String cat){
        MyPanel panel = new MyPanel(cat);
        buttonPanel.setVisible(false);
        add(panel);
    }

    public static void chooseCat(){
        buttonPanel.setVisible(true);
    }
}
