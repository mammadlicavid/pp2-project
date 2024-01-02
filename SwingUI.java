import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SwingUI {
    private static User user;
    private static ArrayList<Movie> watchListMovies;
    private static ArrayList<Movie> allMovies;
    private static int sortButtonClicks = 0;

    public static void generateUI(MovieDatabase globalMovieDatabase) {
        allMovies = globalMovieDatabase.getMovies();

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
                        user = loggedInUser;
                        createMovieSearchFrame(globalMovieDatabase);
                        loginFrame.dispose();
                        mainFrame.setVisible(false);
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
                        user = registredUser;
                        createMovieSearchFrame(globalMovieDatabase);
                        registerFrame.dispose();
                        mainFrame.setVisible(false);
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
        watchListMovies = user.getWatchlist();
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
            filterMovies(globalMovieDatabase, globalMovieDatabase.getMovies(), movieListPanel, "database");

            for (int i = 0; i < globalMovieDatabase.getMovies().size(); i++) {
                // get name to be displayed in the label
                JLabel movieLabel = new JLabel(globalMovieDatabase.getMovies().get(i).toString());
                JButton addButton = new JButton("Add movie to watchlist");

                int finalI = i;
                // for every movie, there is a label and ad button
                // after a button is clicked, if the movie is not already in the watchlist, it
                // is added
                addButton.addActionListener(actionEvent -> {
                    Movie movieObjectByTitle = Movie
                            .getMovieByTitle(globalMovieDatabase.getMovies().get(finalI).getTitle());
                    boolean movieAdded = user.addMovieToWatchlist(movieObjectByTitle);
                    if (movieAdded) {
                        JOptionPane.showMessageDialog(null,
                                "Added to watchlist: " + globalMovieDatabase.getMovies().get(finalI).getTitle());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Movie " + globalMovieDatabase.getMovies().get(finalI).getTitle()
                                        + " already exists in watchlist");
                    }
                });

                // design the locations of items in the frame
                GridBagConstraints gbcLabel = new GridBagConstraints();
                gbcLabel.gridx = 0;
                gbcLabel.gridy = i + 6;
                gbcLabel.weightx = 1; // Make label take available horizontal space
                gbcLabel.fill = GridBagConstraints.HORIZONTAL;
                movieListPanel.add(movieLabel, gbcLabel);

                GridBagConstraints gbcButton = new GridBagConstraints();
                gbcButton.gridx = 1;
                gbcButton.gridy = i + 6;
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
            filterMovies(globalMovieDatabase, user.getWatchlist(), watchlistPanel, "watchlist");

            for (int i = 0; i < user.getWatchlist().size(); i++) {
                JLabel watchlistLabel = new JLabel(user.getWatchlist().get(i).toString());
                JButton removeButton = new JButton("Remove from watchlist");

                int finalI = i;
                // for every movie, there is a label and remove button
                // after the button is clicked, if the movie is removed from wathclist
                removeButton.addActionListener(actionEvent -> {
                    String titleOfMovie = user.getWatchlist().get(finalI).getTitle();

                    user.removeMovieFromWatchlistByTitle(titleOfMovie);
                    JOptionPane.showMessageDialog(null,
                            "Removed from watchlist: " + titleOfMovie);

                    // remove label and button from screen, after it is removed from watchlist
                    removeFromAndinsertIntoPanel(globalMovieDatabase, user.getWatchlist(), -1000, 3000, 0, 5000,
                            watchlistPanel, "watchlist");

                });

                watchlistLabel.setPreferredSize(new Dimension(250, 40));
                removeButton.setPreferredSize(new Dimension(250, 40));

                // design the locations of items in the frame
                GridBagConstraints gbcLabel = new GridBagConstraints();
                gbcLabel.gridx = 0;
                gbcLabel.gridy = i + 6;
                gbcLabel.weightx = 1; // Make label take available horizontal space
                gbcLabel.fill = GridBagConstraints.HORIZONTAL;
                watchlistPanel.add(watchlistLabel, gbcLabel);

                GridBagConstraints gbcButton = new GridBagConstraints();
                gbcButton.gridx = 1;
                gbcButton.gridy = i + 6;
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

    // generate filter releated labels, texts and button
    private static Object[] addFilterComponentsToPanelAndReturnMainComponents(JPanel movieListPanel) {
        JLabel filterMovieLabel = new JLabel("Filter Movies");
        JButton filterButton = new JButton("Click to filter");

        JLabel sortMovieLabel = new JLabel("Sort Movies");
        JButton sortButton = new JButton("Click to Sort");

        JLabel minStartDate = new JLabel("Earliest Start Date");
        JLabel maxStartDate = new JLabel("Latest Start Date");
        JLabel minRunningTime = new JLabel("Smallest Running Time");
        JLabel maxRunningTime = new JLabel("Biggest Running Time");

        // make sure only integers are given as input
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        JFormattedTextField minStartDateTextField = new JFormattedTextField(formatter);
        JFormattedTextField maxStartDateTextField = new JFormattedTextField(formatter);
        JFormattedTextField minRunningTimeTextField = new JFormattedTextField(formatter);
        JFormattedTextField maxRunningTimeTextField = new JFormattedTextField(formatter);

        minStartDateTextField.setColumns(11);
        maxStartDateTextField.setColumns(11);
        minRunningTimeTextField.setColumns(11);
        maxRunningTimeTextField.setColumns(11);

        GridBagConstraints gbcFilterLabel = new GridBagConstraints();
        gbcFilterLabel.gridx = 0;
        gbcFilterLabel.gridy = 4;
        movieListPanel.add(filterMovieLabel, gbcFilterLabel);

        GridBagConstraints gbcFilterButton = new GridBagConstraints();
        gbcFilterButton.gridx = 1;
        gbcFilterButton.gridy = 4;
        movieListPanel.add(filterButton, gbcFilterButton);

        GridBagConstraints gbcMinStartDateLabel = new GridBagConstraints();
        gbcMinStartDateLabel.gridx = 0;
        gbcMinStartDateLabel.gridy = 0;
        movieListPanel.add(minStartDate, gbcMinStartDateLabel);

        GridBagConstraints gbcMinStartDateTextField = new GridBagConstraints();
        gbcMinStartDateTextField.gridx = 1;
        gbcMinStartDateTextField.gridy = 0;
        movieListPanel.add(minStartDateTextField, gbcMinStartDateTextField);

        GridBagConstraints gbcMaxStartDateLabel = new GridBagConstraints();
        gbcMaxStartDateLabel.gridx = 0;
        gbcMaxStartDateLabel.gridy = 1;
        movieListPanel.add(maxStartDate, gbcMaxStartDateLabel);

        GridBagConstraints gbcMaxStartDateTextField = new GridBagConstraints();
        gbcMaxStartDateTextField.gridx = 1;
        gbcMaxStartDateTextField.gridy = 1;
        movieListPanel.add(maxStartDateTextField, gbcMaxStartDateTextField);

        GridBagConstraints gbcMinRunningTimeLabel = new GridBagConstraints();
        gbcMinRunningTimeLabel.gridx = 0;
        gbcMinRunningTimeLabel.gridy = 2;
        movieListPanel.add(minRunningTime, gbcMinRunningTimeLabel);

        GridBagConstraints gbcMinRunningTimeTextField = new GridBagConstraints();
        gbcMinRunningTimeTextField.gridx = 1;
        gbcMinRunningTimeTextField.gridy = 2;
        movieListPanel.add(minRunningTimeTextField, gbcMinRunningTimeTextField);

        GridBagConstraints gbcMaxRunningTimeLabel = new GridBagConstraints();
        gbcMaxRunningTimeLabel.gridx = 0;
        gbcMaxRunningTimeLabel.gridy = 3;
        movieListPanel.add(maxRunningTime, gbcMaxRunningTimeLabel);

        GridBagConstraints gbcMaxRunningTimeTextField = new GridBagConstraints();
        gbcMaxRunningTimeTextField.gridx = 1;
        gbcMaxRunningTimeTextField.gridy = 3;
        movieListPanel.add(maxRunningTimeTextField, gbcMaxRunningTimeTextField);

        GridBagConstraints gbcSortLabel = new GridBagConstraints();
        gbcSortLabel.gridx = 0;
        gbcSortLabel.gridy = 5;
        movieListPanel.add(sortMovieLabel, gbcSortLabel);

        GridBagConstraints gbcSortButton = new GridBagConstraints();
        gbcSortButton.gridx = 1;
        gbcSortButton.gridy = 5;
        movieListPanel.add(sortButton, gbcSortButton);

        // Return the components in an array or any suitable data structure
        return new Object[] { filterButton, sortButton, minStartDateTextField, maxStartDateTextField,
                minRunningTimeTextField,
                maxRunningTimeTextField };
    }

    private static void filterMovies(MovieDatabase globalMovieDatabase, ArrayList<Movie> moviesToBeFiltered,
            JPanel movieListPanel, String panelName) {

        Object[] components = addFilterComponentsToPanelAndReturnMainComponents(movieListPanel);
        JButton filterButton = (JButton) components[0];
        JButton sortButton = (JButton) components[1];
        JTextField minStartDateTextField = (JTextField) components[2];
        JTextField maxStartDateTextField = (JTextField) components[3];
        JTextField minRunningTimeTextField = (JTextField) components[4];
        JTextField maxRunningTimeTextField = (JTextField) components[5];

        filterButton.addActionListener(actionEvent -> {
            // remove all rows except filter-related
            // first get the values, then remove all commas as in the formas we used, there
            // could be commas and recieve the integer values
            int minStartDate = parseTextField(minStartDateTextField.getText(), Integer.MIN_VALUE);
            int maxStartDate = parseTextField(maxStartDateTextField.getText(), Integer.MAX_VALUE);
            int minRunningTime = parseTextField(minRunningTimeTextField.getText(), Integer.MIN_VALUE);
            int maxRunningTime = parseTextField(maxRunningTimeTextField.getText(), Integer.MAX_VALUE);
            removeFromAndinsertIntoPanel(globalMovieDatabase, moviesToBeFiltered, minStartDate, maxStartDate,
                    minRunningTime, maxRunningTime, movieListPanel, panelName);

        });

        sortButton.addActionListener(actionEvent -> {
            sortButtonClicks++;
            removeAllExceptFilterComponents(movieListPanel);
            if (panelName == "database") {
                // sort movies based on number if clicks. If it is even, or odd, it either sorts
                // in increasing or decreasing order
                SwingUI.allMovies = MovieDatabase.getMoviesSortedByRunningTime(SwingUI.allMovies,
                        (sortButtonClicks % 2 == 0) ? true : false);

                for (int i = 0; i < allMovies.size(); i++) {
                    // get name to be displayed in the label
                    JLabel movieLabel = new JLabel(allMovies.get(i).toString());
                    JButton addButton = new JButton("Add movie to watchlist");

                    int finalI = i;
                    addButton.addActionListener(actionEvent2 -> {
                        Movie movieObjectByTitle = Movie.getMovieByTitle(allMovies.get(finalI).getTitle());
                        boolean movieAdded = user.addMovieToWatchlist(movieObjectByTitle);
                        if (movieAdded) {
                            JOptionPane.showMessageDialog(null,
                                    "Added to watchlist: " + allMovies.get(finalI).getTitle());
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Movie " + allMovies.get(finalI).getTitle()
                                            + " already exists in watchlist");
                        }
                    });

                    // design the locations of items in the frame
                    GridBagConstraints gbcLabel = new GridBagConstraints();
                    gbcLabel.gridx = 0;
                    gbcLabel.gridy = i + 6;
                    gbcLabel.weightx = 1; // Make label take available horizontal space
                    gbcLabel.fill = GridBagConstraints.HORIZONTAL;
                    movieListPanel.add(movieLabel, gbcLabel);

                    GridBagConstraints gbcButton = new GridBagConstraints();
                    gbcButton.gridx = 1;
                    gbcButton.gridy = i + 6;
                    movieListPanel.add(addButton, gbcButton);

                }
            } else {
                // sort movies based on number if clicks. If it is even, or odd, it either sorts
                // in increasing or decreasing order
                SwingUI.watchListMovies = MovieDatabase.getMoviesSortedByRunningTime(SwingUI.watchListMovies,
                        (sortButtonClicks % 2 == 0) ? true : false);
                ;
                for (int i = 0; i < watchListMovies.size(); i++) {
                    JLabel watchlistLabel = new JLabel(watchListMovies.get(i).toString());
                    JButton removeButton = new JButton("Remove from watchlist");

                    int finalI = i;
                    // for every movie, there is a label and remove button
                    // after the button is clicked, if the movie is removed from wathclist
                    removeButton.addActionListener(actionEvent3 -> {
                        String titleOfMovie = watchListMovies.get(finalI).getTitle();

                        user.removeMovieFromWatchlistByTitle(titleOfMovie);
                        JOptionPane.showMessageDialog(null,
                                "Removed from watchlist: " + titleOfMovie);

                        // remove label and button from screen, after it is removed from watchlist
                        removeFromAndinsertIntoPanel(globalMovieDatabase, watchListMovies, -1000, 3000, 0, 5000,
                                movieListPanel, "watchlist");

                    });

                    watchlistLabel.setPreferredSize(new Dimension(250, 40));
                    removeButton.setPreferredSize(new Dimension(250, 40));

                    // design the locations of items in the frame
                    GridBagConstraints gbcLabel = new GridBagConstraints();
                    gbcLabel.gridx = 0;
                    gbcLabel.gridy = i + 6;
                    gbcLabel.weightx = 1; // Make label take available horizontal space
                    gbcLabel.fill = GridBagConstraints.HORIZONTAL;
                    movieListPanel.add(watchlistLabel, gbcLabel);

                    GridBagConstraints gbcButton = new GridBagConstraints();
                    gbcButton.gridx = 1;
                    gbcButton.gridy = i + 6;
                    movieListPanel.add(removeButton, gbcButton);
                }
            }
        });

    }

    // remove all componentx except the components related to filtering
    private static void removeAllExceptFilterComponents(JPanel movieListPanel) {
        Component[] components = movieListPanel.getComponents();
        for (Component component : components) {
            if ((component instanceof JLabel) &&
                    !((JLabel) component).getText().matches(
                            "Filter Movies|Earliest Start Date|Latest Start Date|Smallest Running Time|Biggest Running Time|Sort Movies")) {
                movieListPanel.remove(component);
            } else if ((component instanceof JButton) &&
                    (((JButton) component).getText().equals("Add movie to watchlist") ||
                            ((JButton) component).getText().equals("Remove from watchlist"))) {
                movieListPanel.remove(component);
            }
        }

        movieListPanel.revalidate();
        movieListPanel.repaint();
    }

    private static void removeFromAndinsertIntoPanel(MovieDatabase globalMovieDatabase,
            ArrayList<Movie> moviesToBeFiltered,
            int minStartDate, int maxStartDate, int minRunningTime, int maxRunningTime, JPanel movieListPanel,
            String panelName) {

        removeAllExceptFilterComponents(movieListPanel);

        ArrayList<Movie> filteredMovies = MovieDatabase.filterByRunningTimeAndReleaseYear(moviesToBeFiltered,
                minStartDate,
                maxStartDate, minRunningTime, maxRunningTime);

        // based on pnael name, insert the components
        if (panelName == "database") {
            SwingUI.allMovies = moviesToBeFiltered;

            for (int i = 0; i < filteredMovies.size(); i++) {
                // get name to be displayed in the label
                JLabel movieLabel = new JLabel(filteredMovies.get(i).toString());
                JButton addButton = new JButton("Add movie to watchlist");

                int finalI = i;
                // for every movie, there is a label and ad button
                // after a button is clicked, if the movie is not already in the watchlist, it
                // is added
                addButton.addActionListener(actionEvent2 -> {
                    Movie movieObjectByTitle = Movie.getMovieByTitle(filteredMovies.get(finalI).getTitle());
                    boolean movieAdded = user.addMovieToWatchlist(movieObjectByTitle);
                    if (movieAdded) {
                        JOptionPane.showMessageDialog(null,
                                "Added to watchlist: " + filteredMovies.get(finalI).getTitle());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Movie " + filteredMovies.get(finalI).getTitle()
                                        + " already exists in watchlist");
                    }
                });

                // design the locations of items in the frame
                GridBagConstraints gbcLabel = new GridBagConstraints();
                gbcLabel.gridx = 0;
                gbcLabel.gridy = i + 6;
                gbcLabel.weightx = 1; // Make label take available horizontal space
                gbcLabel.fill = GridBagConstraints.HORIZONTAL;
                movieListPanel.add(movieLabel, gbcLabel);

                GridBagConstraints gbcButton = new GridBagConstraints();
                gbcButton.gridx = 1;
                gbcButton.gridy = i + 6;
                movieListPanel.add(addButton, gbcButton);

            }
        } else {
            SwingUI.watchListMovies = moviesToBeFiltered;
            for (int i = 0; i < filteredMovies.size(); i++) {
                JLabel watchlistLabel = new JLabel(filteredMovies.get(i).toString());
                JButton removeButton = new JButton("Remove from watchlist");

                int finalI = i;
                // for every movie, there is a label and remove button
                // after the button is clicked, if the movie is removed from wathclist
                removeButton.addActionListener(actionEvent3 -> {
                    String titleOfMovie = filteredMovies.get(finalI).getTitle();

                    user.removeMovieFromWatchlistByTitle(titleOfMovie);
                    JOptionPane.showMessageDialog(null,
                            "Removed from watchlist: " + titleOfMovie);

                    // remove label and button from screen, after it is removed from watchlist
                    removeFromAndinsertIntoPanel(globalMovieDatabase, filteredMovies, -1000, 3000, 0, 5000,
                            movieListPanel, "watchlist");

                });

                watchlistLabel.setPreferredSize(new Dimension(250, 40));
                removeButton.setPreferredSize(new Dimension(250, 40));

                // design the locations of items in the frame
                GridBagConstraints gbcLabel = new GridBagConstraints();
                gbcLabel.gridx = 0;
                gbcLabel.gridy = i + 6;
                gbcLabel.weightx = 1; // Make label take available horizontal space
                gbcLabel.fill = GridBagConstraints.HORIZONTAL;
                movieListPanel.add(watchlistLabel, gbcLabel);

                GridBagConstraints gbcButton = new GridBagConstraints();
                gbcButton.gridx = 1;
                gbcButton.gridy = i + 6;
                movieListPanel.add(removeButton, gbcButton);
            }
        }
    }

    // helper function for parsing user input
    // so that if there is no input, we can assume that that value is not going tobe
    // filtered
    private static int parseTextField(String text, int defaultValue) {
        String cleanedText = text.replaceAll(",", "");
        if (cleanedText.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(cleanedText);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
