import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import report.ReportHandler;

import javax.swing.JPasswordField;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Logger;

public class LoginView extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JPanel panel;
    private LoginHandler loginHandler;

    public LoginView() {
        panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setPreferredSize(new Dimension(300, 150));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
        
        loginHandler = new LoginHandler("users.ser");
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());

        add(panel);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (loginHandler.authenticate(username, password)) {
            // Usuń poprzedni panel logowania
            panel.setVisible(false);

            // Pokaż quiz
            CategoryView categoryView = new CategoryView();
            add(categoryView);
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Logger logger = ReportHandler.getLogger();
        if (password.trim().isEmpty()) {
            logger.warning("Attempted to register with a blank password.");
            JOptionPane.showMessageDialog(this, "Password can't be blank.");
        }
        else if (!loginHandler.doesUserExist(username)) {
            loginHandler.addUser(username, password);
        }
        else {
            logger.warning("Attempted to register with an existing username: " + username);
            JOptionPane.showMessageDialog(this, "User already exists.");
        }
    }
}
