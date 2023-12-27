import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingUI {
    public static void genereateUI() {
        // creating main frame
        JFrame mainFrame = new JFrame("Login/Register GUI");
        mainFrame.setSize(400, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // creating main panel
        JPanel mainPanel = new JPanel();

        // login button action to generate another frame/panel
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame loginFrame = new JFrame("Login to System");
                loginFrame.setSize(400, 400);
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // creating panel
                JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                // creating labels and input fields
                JLabel usernameLabel = new JLabel("Username");
                JTextField usernameField = new JTextField(10);

                JLabel passwordLabel = new JLabel("Password");
                JTextField passwordField = new JTextField(10);

                JButton loginButtonInFrame = new JButton("Login");

                // check the previously defined login function and either login the user or fail
                // it
                loginButtonInFrame.addActionListener(ev -> {
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    User loggedInUser = User.login(username, password);

                    if (loggedInUser != null) {
                        JOptionPane.showMessageDialog(null,
                                "Login successful! Welcome back, " + loggedInUser.getName());
                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed!");
                    }
                });

                loginPanel.add(usernameLabel);
                loginPanel.add(usernameField);
                loginPanel.add(passwordLabel);
                loginPanel.add(passwordField);
                loginPanel.add(loginButtonInFrame);
                loginFrame.add(loginPanel);
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame registerFrame = new JFrame("Register to System");
                registerFrame.setSize(400, 400);
                registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // creating panel
                JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                // creating labels and input fields
                JLabel nameLabel = new JLabel("Name");
                JTextField nameField = new JTextField(11);

                JLabel usernameLabel = new JLabel("Username");
                JTextField usernameField = new JTextField(11);

                JLabel passwordLabel = new JLabel("Password");
                JTextField passwordField = new JTextField(11);

                // register button inside register panel
                JButton registerButtonInFrame = new JButton("Register");

                // check the previously defined registerUser function and either create a new
                // user or fail the registration
                registerButtonInFrame.addActionListener(ev -> {
                    String name = nameField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    boolean registrationSuccess = User.registerUser(new User(name, username, password));

                    if (registrationSuccess) {
                        JOptionPane.showMessageDialog(null, ("Registration successful! Welcome, " + name));
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration failed!");
                    }
                });

                // add parts to panel
                registerPanel.add(nameLabel);
                registerPanel.add(nameField);
                registerPanel.add(usernameLabel);
                registerPanel.add(usernameField);
                registerPanel.add(passwordLabel);
                registerPanel.add(passwordField);
                registerPanel.add(registerButtonInFrame);
                registerFrame.add(registerPanel);
                registerFrame.setLocationRelativeTo(null);
                registerFrame.setVisible(true);
            }
        });

        // add buttons to main manel
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        mainFrame.add(mainPanel);

        mainFrame.setVisible(true);
    }
}
