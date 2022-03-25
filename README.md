# moviesdb
An application similar to IMDB where you can browse movies, actors...

## Screenshots

Browse Movies             |  Browse Actors
:-------------------------:|:-------------------------:
![](./docs/images/homepage.png)  |  ![](./docs/images/actors.png)
Movie Details             |  Actor Details
![](./docs/images/movie-details-1.png)  |  ![](./docs/images/actor-details.png)
Movie Details             |  Movie Details - save to playlists
![](./docs/images/movie-details-2.png)  |  ![](./docs/images/save-playlists.png)
Playlists             |  Playlist Details
![](./docs/images/playlists.png)  |  ![](./docs/images/playlist-details.png)

## How to run the application
I've used Postgres as database. In project root path you can find two files `schema.sql` and `data.sql`. Execute statements inside `schema.sql` to create necessary tables. Than execute statements in `data.sql` to insert some data (with some real data that belongs to IMDB).

As for the application, the application is built with SpringBoot (using maven). To start the application, just run the main class as any other SpringBoot app. Than visit `localhost:8080` in browser. You can then browse movies and actors. If you want to create your own playlists with movies saved or if you want to rate movies, you need to be logged in. You can login with a already inserted user in database, or you can register at `localhost:8080/users/register`.

```
Default created user:
username: user1
password: 123
```
