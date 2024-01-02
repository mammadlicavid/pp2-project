import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingUI {
    private static User user;

    public static void generateUI(MovieDatabase globalMovieDatabase) {

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

                        // create a new frame for movie search after successful login. This will open
                        // new frame and close previous ones
                        createMovieSearchFrame(globalMovieDatabase);
                        loginFrame.dispose();
                        mainFrame.setVisible(false);
                        user = loggedInUser;
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

                // Creating panel
                JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                // Creating labels and input fields
                JLabel nameLabel = new JLabel("Name");
                JTextField nameField = new JTextField(11);

                JLabel usernameLabel = new JLabel("Username");
                JTextField usernameField = new JTextField(11);

                JLabel passwordLabel = new JLabel("Password");
                JTextField passwordField = new JTextField(11);

                // Register button inside register panel
                JButton registerButtonInFrame = new JButton("Register");

                // Check the previously defined registerUser function and either create a new
                // user or fail the registration
                registerButtonInFrame.addActionListener(ev -> {
                    String name = nameField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    User registredUser = User.registerUser(new User(name, username, password));

                    if (registredUser != null) {
                        JOptionPane.showMessageDialog(null, ("Registration successful! Welcome, " + name));

                        // create a new frame for movie search after successful registration. This will
                        // open new frame and close previous ones
                        createMovieSearchFrame(globalMovieDatabase);
                        registerFrame.dispose();
                        mainFrame.setVisible(false);
                        user = registredUser;
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

        // add buttons to main panel
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    // method to be used to create new panel after succesfull login / registration
    private static void createMovieSearchFrame(MovieDatabase globalMovieDatabase) {
        JFrame searchFrame = new JFrame("Movie Search");
        searchFrame.setSize(400, 400);
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton searchMovieButton = new JButton("Search a movie");
        JButton myWatchlistButton = new JButton("My Watchlist");
        searchPanel.add(searchMovieButton);
        searchPanel.add(myWatchlistButton);
        searchFrame.add(searchPanel);

        // searching movie
        searchMovieButton.addActionListener(e -> {
            JFrame movieListFrame = new JFrame("Movie List");
            movieListFrame.setSize(800, 800);

            JPanel movieListPanel = new JPanel(new GridBagLayout());

            for (int i = 0; i < globalMovieDatabase.movies.size(); i++) {
                // get name to be displayed in the label
                JLabel movieLabel = new JLabel(globalMovieDatabase.movies.get(i).toString());
                JButton addButton = new JButton("Add movie to watchlist");

                int finalI = i;
                // for every movie, there is a label and ad button
                // after a button is clicked, if the movie is not already in the watchlist, it
                // is added
                addButton.addActionListener(actionEvent -> {
                    Movie movieObjectByTitle = Movie.getMovieByTitle(globalMovieDatabase.movies.get(finalI).getTitle());
                    boolean movieAdded = user.addMovieToWatchlist(movieObjectByTitle);
                    if (movieAdded) {
                        JOptionPane.showMessageDialog(null,
                                "Added to watchlist: " + globalMovieDatabase.movies.get(finalI).getTitle());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Movie " + globalMovieDatabase.movies.get(finalI).getTitle()
                                        + " already exists in watchlist");
                    }
                });

                // design the locations of items in the frame
                GridBagConstraints gbcLabel = new GridBagConstraints();
                gbcLabel.gridx = 0;
                gbcLabel.gridy = i;
                gbcLabel.weightx = 1; // Make label take available horizontal space
                gbcLabel.fill = GridBagConstraints.HORIZONTAL;
                movieListPanel.add(movieLabel, gbcLabel);

                GridBagConstraints gbcButton = new GridBagConstraints();
                gbcButton.gridx = 1;
                gbcButton.gridy = i;
                movieListPanel.add(addButton, gbcButton);
            }

            JScrollPane scrollPane = new JScrollPane(movieListPanel);

            movieListFrame.add(scrollPane);
            movieListFrame.setVisible(true);
            movieListFrame.setLocationRelativeTo(searchFrame);
        });

        myWatchlistButton.addActionListener(e -> {
            JFrame watchlistFrame = new JFrame("My Watchlist");
            watchlistFrame.setSize(800, 800);

            JPanel watchlistPanel = new JPanel(new GridBagLayout());

            for (int i = 0; i < user.getWatcArrayList().size(); i++) {
                JLabel watchlistLabel = new JLabel(user.getWatcArrayList().get(i).toString());
                JButton removeButton = new JButton("Remove from watchlist");

                int finalI = i;
                // for every movie, there is a label and remove button
                // after the button is clicked, if the movie is removed from wathclist
                removeButton.addActionListener(actionEvent -> {
                    user.removeMovieFromWatchlistByTitle(globalMovieDatabase.movies.get(finalI).getTitle());
                    JOptionPane.showMessageDialog(null,
                            "Removed from watchlist: " + globalMovieDatabase.movies.get(finalI).getTitle());

                    // remove label and button from screen, after it is removed from watchlist
                    watchlistLabel.setVisible(false);
                    removeButton.setVisible(false);

                });

                watchlistLabel.setPreferredSize(new Dimension(250, 40));
                removeButton.setPreferredSize(new Dimension(250, 40));

                // design the locations of items in the frame
                GridBagConstraints gbcLabel = new GridBagConstraints();
                gbcLabel.gridx = 0;
                gbcLabel.gridy = i;
                gbcLabel.weightx = 1; // Make label take available horizontal space
                gbcLabel.fill = GridBagConstraints.HORIZONTAL;
                watchlistPanel.add(watchlistLabel, gbcLabel);

                GridBagConstraints gbcButton = new GridBagConstraints();
                gbcButton.gridx = 1;
                gbcButton.gridy = i;
                watchlistPanel.add(removeButton, gbcButton);
            }

            JScrollPane scrollPane = new JScrollPane(watchlistPanel);

            watchlistFrame.add(scrollPane);
            watchlistFrame.setVisible(true);
            watchlistFrame.setLocationRelativeTo(myWatchlistButton);

        });

        searchFrame.setVisible(true);
        searchFrame.setLocationRelativeTo(null);
    }

}
