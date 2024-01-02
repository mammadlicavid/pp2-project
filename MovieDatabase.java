import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class MovieDatabase implements Serializable {
    private ArrayList<Movie> movies;

    public MovieDatabase() {
        this.movies = new ArrayList<Movie>();
        // upon object creation, read all movies
        loadMoviesFromFile();
    }

    public void addMovie(Movie movie) {
        Iterator<Movie> iterator = movies.iterator();
        // check if movie already exist in db (Here, it is assumed that movie named are
        // unique)
        while (iterator.hasNext()) {
            Movie movie2 = iterator.next();
            if (movie2.getTitle().equals(movie.getTitle())) {
                return;
            }
        }
        movies.add(movie);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    public void removeMovieByTitle(String title) {
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            if (movie.getTitle().equals(title)) {
                iterator.remove();
                return;
            }
        }
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    private void loadMoviesFromFile() {
        // read all Movie objects from movies.txt
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("movies.txt"))) {
            while (true) {
                try {
                    Movie movie = (Movie) ois.readObject();
                    if (movie != null) {
                        addMovie(movie);
                    }
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException | IOException e) {
                    System.out.println("Error reading movie from file: " + e.getMessage());
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading movies from file: " + e.getMessage());
        }
    }

    // filter functions
    public ArrayList<Movie> filterByReleaseYear(int year) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        // get all movies whose release yeae is in given year
        for (Movie movie : this.getMovies()) {
            if (movie.getReleaseYear() == year) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    public static ArrayList<Movie> filterByReleaseYear(ArrayList<Movie> movies, int startYear, int endYear) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        // get all movies whose release year is in between given years
        for (Movie movie : movies) {
            if (movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    public static ArrayList<Movie> filterByRunningTimeInMinutes(ArrayList<Movie> movies, int minTime, int maxTime) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        // get all movies whose running time is in between given years
        for (Movie movie : movies) {
            if (movie.getRunningTime() >= minTime && movie.getRunningTime() <= maxTime) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    public static ArrayList<Movie> filterByRunningTimeAndReleaseYear(ArrayList<Movie> movies, int startYear,
            int endYear, int minTime,
            int maxTime) {
        ArrayList<Movie> moviesFilteredByReleaseYear = filterByReleaseYear(movies, startYear, endYear);
        ArrayList<Movie> moviesFilteredByRunningTimeAndReleaseYear = filterByRunningTimeInMinutes(
                moviesFilteredByReleaseYear, minTime, maxTime);

        return moviesFilteredByRunningTimeAndReleaseYear;
    }

    public ArrayList<Movie> filterByDirector(String name) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        // get all movies whose director has given name (not case sensitive)
        for (Movie movie : this.getMovies()) {
            if (movie.getDirector().toLowerCase().contains(name.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    public ArrayList<Movie> filterByTitle(String title) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        // get all movies whose title has given text (not case sensitive)
        for (Movie movie : this.getMovies()) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }

        return filteredMovies;
    }

    // sort functions

    public ArrayList<Movie> getMoviesSortedByReleaseYear(boolean increasingOrder) {
        ArrayList<Movie> sortedMovies = new ArrayList<>(movies);
        // get sorted movies in either increasing or decrasing order by their release
        // year
        if (increasingOrder) {
            for (int i = 0; i < sortedMovies.size() - 1; i++) {
                for (int j = i + 1; j < sortedMovies.size(); j++) {
                    if (sortedMovies.get(i).getReleaseYear() < sortedMovies.get(j).getReleaseYear()) {
                        Movie temp = sortedMovies.get(j);
                        sortedMovies.set(j, sortedMovies.get(j + 1));
                        sortedMovies.set(j + 1, temp);
                    }
                }
            }
        } else {
            for (int i = 0; i < sortedMovies.size() - 1; i++) {
                for (int j = i + 1; j < sortedMovies.size(); j++) {
                    if (sortedMovies.get(i).getReleaseYear() > sortedMovies.get(j).getReleaseYear()) {
                        Movie temp = sortedMovies.get(j);
                        sortedMovies.set(j, sortedMovies.get(j + 1));
                        sortedMovies.set(j + 1, temp);
                    }
                }
            }
        }

        return sortedMovies;
    }

    public ArrayList<Movie> getMoviesSortedByRunningTime(boolean increasingOrder) {
        ArrayList<Movie> sortedMovies = new ArrayList<>(movies);
        // get sorted movies in either increasing or decrasing order by their running
        // time
        if (increasingOrder) {
            for (int i = 0; i < sortedMovies.size() - 1; i++) {
                for (int j = i + 1; j < sortedMovies.size(); j++) {
                    if (sortedMovies.get(i).getRunningTime() < sortedMovies.get(j).getRunningTime()) {
                        Movie temp = sortedMovies.get(j);
                        sortedMovies.set(j, sortedMovies.get(j + 1));
                        sortedMovies.set(j + 1, temp);
                    }
                }
            }
        } else {
            for (int i = 0; i < sortedMovies.size() - 1; i++) {
                for (int j = i + 1; j < sortedMovies.size(); j++) {
                    if (sortedMovies.get(i).getRunningTime() > sortedMovies.get(j).getRunningTime()) {
                        Movie temp = sortedMovies.get(j);
                        sortedMovies.set(j, sortedMovies.get(j + 1));
                        sortedMovies.set(j + 1, temp);
                    }
                }
            }
        }

        return sortedMovies;
    }
}
