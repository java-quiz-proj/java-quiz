import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Main{

    public static void main(String[] args) {
        JButton startBtn;
        JFrame frame = new JFrame("Quiz");
        startBtn = new JButton("START");
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.add(startBtn);

        startBtn.addActionListener(e -> {
            panel.setVisible(false);
            LoginView loginView = new LoginView(frame);
            frame.add(loginView);
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 300);
        frame.setVisible(true);

    }
}



