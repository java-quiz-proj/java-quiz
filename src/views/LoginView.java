package views;

import javax.swing.*;

import utils.LoginHandler;
import utils.ReportHandler;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Logger;

public class LoginView extends JPanel {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JPanel panel;
    private LoginHandler loginHandler;
    private static final Logger logger = ReportHandler.getLogger();

    public LoginView(JFrame frame) {
        panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setPreferredSize(new Dimension(400, 150));

        this.frame = frame;
        JLabel usernameLabel = new JLabel("Nick:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Hasło:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Zaloguj");
        registerButton = new JButton("Zarejestruj");

        // add elements to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginHandler = new LoginHandler("users.ser"); // Set loginHandler

        // Bind buttons to actions
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());

        add(panel);
        logger.info("LoginView added to frame");
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (loginHandler.authenticate(username, password)) {
                // Hide previous login panel
                panel.setVisible(false);

                // Show menu
                MenuView menuView = new MenuView(frame);
                add(menuView);
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Nieprawidłowy nick lub hasło.");
            }
        } catch (Exception ex) {
            logger.severe("Error during login process: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Coś poszło nie taj. Spróbuj ponownie.");
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            if (username.trim().length() < 3) {
                logger.warning("Attempted to register with a username shorter than 3 characters.");
                JOptionPane.showMessageDialog(this, "Nick musi składać się z przynajmniej trzech liter.");
            } else if (password.trim().isEmpty()) {
                logger.warning("Attempted to register with a blank password.");
                JOptionPane.showMessageDialog(this, "Hasło nie może być puste.");
            } else if (!loginHandler.doesUserExist(username)) {
                loginHandler.addUser(username, password);
                // Automatic login after successful registration
                handleLogin();
            } else {
                logger.warning("Attempted to register with an existing username: " + username);
                JOptionPane.showMessageDialog(this, "Użytkownik o takim nicku już istnieje.");
            }
        } catch (Exception ex) {
            logger.severe("Error during registration process: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Coś poszło nie tak. Spróbuj ponownie.");
        }
    }
}
