import javax.swing.*;

public class Category extends JPanel {
    private static JButton animals, maths;
    public Category(){
        animals = new JButton("Zwierzęta");
        maths = new JButton("Matematyka");
        add(maths);
        add(animals);

        animals.addActionListener(e -> {
            addNewPanel();
        });

        maths.addActionListener(e -> {
            addNewPanel();
        });

    }
        private void addNewPanel(){
            MyPanel panel = new MyPanel();
            animals.setVisible(false);
            maths.setVisible(false);
            add(panel);
        }

        public static void chooseCat(){
            animals.setVisible(true);
            maths.setVisible(true);
        }
}
