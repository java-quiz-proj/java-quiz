
import javax.swing.JFrame;
import javax.swing.JButton;

public class Main{

    public static void main(String[] args) {

        JButton startBtn;
        JFrame frame = new JFrame("Quiz");
        startBtn = new JButton("START");
        frame.add(startBtn);

        startBtn.addActionListener(e -> {
            startBtn.setVisible(false);
            LoginView loginView = new LoginView(frame);
            frame.add(loginView);
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(750, 150);
        frame.setVisible(true);

    }
}