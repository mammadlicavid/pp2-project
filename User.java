import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String userName;
    private String password;
    private ArrayList<Movie> watchlist;

    public User(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.watchlist = new ArrayList<Movie>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addMovieToWatchlist(Movie movie) {
        this.watchlist.add(movie);
        updateUserToFile(); // update the user object in users.txt
    }

    public void removeMovieFromWatchlist(Movie movie) {
        this.watchlist.remove(movie);
    }

    public void removeMovieFromWatchlistByTitle(String title) {
        Iterator<Movie> iterator = watchlist.iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            if (movie.getTitle().equals(title)) {
                iterator.remove();
                return;
            }
        }
    }

    public static User registerUser(User u) {
        try {
            // create users.txt if necessary
            File file = new File("users.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            // check if user already exists in db
            boolean checkIfUserExists = checkIfUserExists(u.userName);
            if (checkIfUserExists) {
                return null;
            }

            // read all users, append newly generated one, and write it all back to file
            List<User> existingUsers = readAllUsers();

            existingUsers.add(u);

            try (FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                for (User user : existingUsers) {
                    oos.writeObject(user);
                }

            }

            return u;
        } catch (IOException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return null;
        }
    }

    public static boolean checkIfUserExists(String username) {
        // go through each user object to find if there is an user username with given
        // string
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.txt"))) {
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    if (user.getUserName().equals(username)) {
                        return true;
                    }
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error reading user from file: " + e.getMessage());
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file named users.txt!");
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public static User login(String username, String password) {
        try {
            FileInputStream fis = new FileInputStream("users.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Go through each user object to find if there is a user with the given
            // username and password
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    // if any user with given username and password exist:
                    if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                        return user;
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Error reading user: " + e.getMessage());
                    break;
                } catch (IOException e) {
                    break;
                }
            }

            ois.close();
            System.out.println("Username & password is wrong");
            return null;

        } catch (IOException e) {
            return null;
        }
    }

    // update the User object
    private void updateUserToFile() {
        try {
            // read all users, update the one with the same username
            List<User> existingUsers = readAllUsers();

            for (int i = 0; i < existingUsers.size(); i++) {
                if (existingUsers.get(i).getUserName().equals(this.getUserName())) {
                    existingUsers.set(i, this);
                    break;
                }
            }

            // write all users, with updated, back to users.txt
            try (FileOutputStream fos = new FileOutputStream("users.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                for (User user : existingUsers) {
                    oos.writeObject(user);
                }

            }
        } catch (IOException e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }
    }

    // helper function to determine if previous error is fixed
    public static List<String> getAllUserNames() {
        // put usernames of all users to a lsit, return it
        ArrayList<String> userNames = new ArrayList<String>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.txt"))) {
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    userNames.add(user.getName());
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Error reading user names from file: " + e.getMessage());
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file named users.txt!");
        } catch (IOException e) {
            System.out.println("Error reading user names from file: " + e.getMessage());
        }

        return userNames;
    }

    private static List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        // read all user objects from users.txt
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.txt"))) {
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    users.add(user);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException | IOException e) {
                    System.out.println("Error reading user from file: " + e.getMessage());
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file named users.txt!");
        } catch (IOException e) {
            return users;
        }

        return users;
    }
}
