public class Main {
    public static void main(String[] args) {
        User u = new User("first", "second", "122345");

        System.out.println(User.checkIfUserExists("second")); // check if it added to db
        System.out.println(User.checkIfUserExists("firsst")); // check if non db file exist in db

        System.out.println(User.login("second", "122345")); // true credentials
        System.out.println(User.login("first", "seccond")); // false credentialss

    }
}
