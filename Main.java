public class Main {
    public static void main(String[] args) {

        // we just found out that the code we were righting was not working properly.
        // the write functions were not working properly, and read funcitons were only
        // reading one object.
        // therefore, we wrote some code below to check, and it finally works

        User user1 = new User("name", "surname", "123");
        User user2 = new User("name1", "surname1", "1233");
        User user3 = new User("name2", "surname12", "1233");

        User.registerUser(user1);
        User.registerUser(user2);
        User.registerUser(user3);

        user1.addMovieToWatchlist(new Movie("title", "director", 0, 0));
        user1.addMovieToWatchlist(new Movie("title2", "director", 0, 0));
        user1.addMovieToWatchlist(new Movie("title3", "director", 0, 0));
        user1.addMovieToWatchlist(new Movie("title4", "director", 0, 0));

        MovieDatabase globalMovieDatabase = new MovieDatabase();
        System.out.println(globalMovieDatabase.getMovies().size());

        System.out.println(User.getAllUserNames().size());
    }
}
