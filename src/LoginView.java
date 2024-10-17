import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.GridLayout;

public class LoginView extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JFrame frame;
    
    public LoginView(JFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(3, 2));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        
        loginButton = new JButton("Login");
        
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        
        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        LoginHandler logInHandler = new LoginHandler("users.txt");

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
}
