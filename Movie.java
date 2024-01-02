import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    private String title;
    private String director;
    private int releaseYear;
    private int runningTime;

    public Movie(String title, String director, int releaseYear, int runningTime) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.runningTime = runningTime;
        writeMovieToFile(); // after generation of object, write it to file
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    private void writeMovieToFile() {
        try {
            // before writing, check if it exists already
            if (checkIfMovieExists()) {
                System.out.println("Movie already exists in the file.");
                return;
            }

            // first read all movies, add the newly generated to exisstingMovies, and put
            // all in the txt file
            // NOTE: in our previous method, the code was nor working properly, so we use
            // this methodology

            List<Movie> existingMovies = readAllMovies();
            existingMovies.add(this);

            try (FileOutputStream fos = new FileOutputStream("movies.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                for (Movie movie : existingMovies) {
                    oos.writeObject(movie);
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing movie to file: " + e.getMessage());
        }
    }

    private boolean checkIfMovieExists() {
        File file = new File("movies.txt");

        // create file if necessary
        if (!file.exists()) {
            return false;
        }

        // get all existing movies from movies.txt and compare their title
        List<Movie> existingMovies = readAllMovies();

        for (Movie movie : existingMovies) {
            if (movie.getTitle().equals(this.title)) {
                return true;
            }
        }
        return false;
    }

    private static List<Movie> readAllMovies() {
        List<Movie> movies = new ArrayList<>();
        // reading all movies from movies.txt
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("movies.txt"))) {
            while (true) {
                try {
                    Movie movie = (Movie) ois.readObject();
                    movies.add(movie);
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException | IOException e) {
                    System.out.println("Error reading movies from file: " + e.getMessage());
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            return movies;
        } catch (IOException e) {
            System.out.println("Error reading movies from file: " + e.getMessage());
        }

        return movies;
    }

    public static Movie getMovieByTitle(String title) {
        List<Movie> existingMovies = readAllMovies();

        for (Movie movie : existingMovies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.title + ", " + this.director + " " + this.releaseYear + " " + this.runningTime;
    }
}
