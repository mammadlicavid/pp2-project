public class Main {
    public static void main(String[] args) {

        // add movies to global movies database (done auto inside Movie constructor)
        Movie movie1 = new Movie("The Shawshank Redemption", "Frank Darabont", 1994, 142);
        Movie movie2 = new Movie("The Godfather", "Francis Ford Coppola", 1972, 175);
        Movie movie3 = new Movie("The Dark Knight", "Christopher Nolan", 2008, 152);
        Movie movie4 = new Movie("Pulp Fiction", "Quentin Tarantino", 1994, 154);
        Movie movie5 = new Movie("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", 2001, 178);
        Movie movie6 = new Movie("Forrest Gump", "Robert Zemeckis", 1994, 142);
        Movie movie7 = new Movie("Inception", "Christopher Nolan", 2010, 148);
        Movie movie8 = new Movie("The Matrix", "Lana Wachowski, Lilly Wachowski", 1999, 136);
        Movie movie9 = new Movie("Schindler's List", "Steven Spielberg", 1993, 195);
        Movie movie10 = new Movie("The Silence of the Lambs", "Jonathan Demme", 1991, 118);
        Movie movie11 = new Movie("Interstellar", "Christopher Nolan", 2014, 169);
        Movie movie12 = new Movie("The Godfather: Part II", "Francis Ford Coppola", 1974, 202);
        Movie movie13 = new Movie("The Lord of the Rings: The Two Towers", "Peter Jackson", 2002, 179);
        Movie movie14 = new Movie("The Pianist", "Roman Polanski", 2002, 150);
        Movie movie15 = new Movie("Fight Club", "David Fincher", 1999, 139);
        Movie movie16 = new Movie("Casablanca", "Michael Curtiz", 1942, 102);
        Movie movie17 = new Movie("The Godfather: Part III", "Francis Ford Coppola", 1990, 162);
        Movie movie18 = new Movie("The Lord of the Rings: The Return of the King", "Peter Jackson", 2003, 201);
        Movie movie19 = new Movie("The Dark Knight Rises", "Christopher Nolan", 2012, 164);
        Movie movie20 = new Movie("Inglourious Basterds", "Quentin Tarantino", 2009, 153);

        MovieDatabase globalMovieDatabase = new MovieDatabase();

        SwingUI.generateUI(globalMovieDatabase);
    }
}
