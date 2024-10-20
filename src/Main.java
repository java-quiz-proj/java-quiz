import report.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Font;
import java.util.logging.Logger;

public class Main{

    public static void main(String[] args) {
        // Ustawienie czcionki
        Font myFont = new Font ("Arial", Font.PLAIN, 24);
        UIManager.put("Button.font", myFont);
        UIManager.put("Label.font", myFont);
        UIManager.put("Panel.font", myFont);
        UIManager.put("PasswordField.font", myFont);
        UIManager.put("TextField.font", myFont);

        Logger logger = ReportHandler.getLogger();
        logger.info("Program został uruchomiony.");

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
        frame.setSize(640, 450);
        frame.setVisible(true);
    }
}



