import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

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

    public static boolean registerUser(User u) {
        try {
            // check if user already exists in db
            boolean checkIfUserExists = checkIfUserExists(u.userName);
            if (checkIfUserExists) {
                System.out.println("The user already exists!");
                return false;
            }

            OutputStream uos = new FileOutputStream("users.txt", true);
            ObjectOutputStream uoos = new ObjectOutputStream(uos);
            uoos.writeObject(u);
            uoos.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file named users.txt!");
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public static boolean checkIfUserExists(String username) {
        try {
            FileInputStream fis = new FileInputStream("users.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            // go through each user object to find if there is an user username with given
            // string
            while (true) {
                try {
                    User user = (User) ois.readObject();

                    if (user.getUserName().equals(username)) {
                        return true;
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Could not find the file named users.txt!");
                } catch (ClassNotFoundException e) {
                    System.out.println("Error reading user from file: " + e.getMessage());
                    break;
                } catch (IOException e) {
                    break;
                }
            }

            ois.close();
            return false;

        } catch (IOException e) {
            return false;
        }
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

}
