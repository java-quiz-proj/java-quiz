
import javax.swing.JFrame;
import javax.swing.JButton;

public class Main{

    public static void main(String[] args) {

        JButton startBtn;
        JFrame frame = new JFrame("Quiz");
        startBtn = new JButton("START");
        frame.add(startBtn);

        startBtn.addActionListener(e -> {
            Category category = new Category();
            startBtn.setVisible(false);
            frame.add(category);
        });

        frame.pack();
        frame.setSize(750, 150);
        frame.setVisible(true);
    }
}



