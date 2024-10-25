import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Dimension;
import java.awt.GridLayout;

public class LoginView extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JFrame frame;
    private LoginHandler logInHandler;

    public LoginView(JFrame frame) {
        this.frame = frame;

        // Ustaw parametry LoginView
        setLayout(new GridLayout(3, 2));
        setMaximumSize(new Dimension(400, 300));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerButton);

        logInHandler = new LoginHandler("users.ser");
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        
        // dodaj LoginView do panelu wyśrodkowującego
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(this);
        panel.add(Box.createVerticalGlue());

        // zaaktualizuj frame
        frame.add(panel);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (logInHandler.authenticate(username, password)) {
            // Usuń poprzedni panel logowania
            frame.getContentPane().removeAll();

            // Pokaż quiz
            CategoryView categoryView = new CategoryView();
            frame.add(categoryView);
            frame.revalidate();
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        logInHandler.addUser(username, password);
    }
}
