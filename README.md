We have different classes, to begin with.

User class: Each user has a username, name, password and watchList, which is an arraylist consisting of movies.
Every used can have a different watchlist of different movies. However, they must be selected from MovieGlobalDatabase

MovieGlobalDatabase is a class, which only has one object in the entire application, is a class for demonstrating list of all classes.
When its object is created, all movies that are listed in the movies.txt are loaded into it, which is then used inside our application.

Movie class: it is a class for demonstrating movies, which has title, direcotr, releaseyear and runningtime. When the movie object is created, its written into movies.txt,
if it is not already created.

Finally, we have a SwingUI function, which is a class for controlling our user interface.
It is the most essential part of our project, as it consists of most of the logic used.

We start by calling its generateUI function and it first created a UI for us.
At the beginning, it asks for either login or register.
If the user already is registered with the same username, then it does not let another registration.
After succesfull registration/login, it opens up another tab for for the users, where the users can search for a movie.

Inside this new tab, there are 2 parts:
Searching he movie in the whole database. It basically drags all the movies from movies.txt using globalMovieDatabase object.
For each movie, there is a button for adding it to user's watchlist.

In the second tab, the users can see their watchlist consisting of movies they added in the first section. Here, they can remove any movie from their database.

Finally, in eahc of these sections, there are functionality of sorting the movies and filtering them.

Filtering:
We can filter based on 4 attributes:
Minimum releaseyear, maximum releaseyear, Minimum runningtime and maximum runningtime.
If a input is not given for any of these, we consider as limitless (for example if maximum is not given, there is no upperbound etc.)
After clicking it, it demonstrates all movies with the filtrtation.

Sorting:
the function can sort the movie sbased on their runningtime. Sorting can be two vay: increasing or decreasing.
Everytime the user clicks on the sort button, it increments to total clicks, and based on whehther the total count is even or odd, it sorts increasingly or decreasingly.

For both of these functionality, when the button is clicked, first it removes every data inside the panel, and adds them again.

Further possible improvements:
- Signing out
- Sorting based on other factors (some of them are actually already implemented in other functions, but not been used/tested)
- Adding/removing a movie to globaldatabase from UI as an admin. This can be achieved by adding users a type of either a normal user or an admin
