package com.moviesdb.actor;

import com.moviesdb.genre.Genre;
import com.moviesdb.movie.model.MovieActor;

import java.util.List;
import java.util.Set;

public record ActorDetailsModel(Actor actor,
                                List<MovieActor> mostPopularMovies,
                                Set<Genre> genresPlayed) {
}
