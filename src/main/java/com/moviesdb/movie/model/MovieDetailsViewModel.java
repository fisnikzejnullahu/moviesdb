package com.moviesdb.movie.model;

import com.moviesdb.playlist.Playlist;

import java.util.List;

public record MovieDetailsViewModel(Movie movie,
                                    List<Playlist> userPlaylists,
                                    short userRate) {

    public MovieDetailsViewModel(Movie movie) {
        this(movie, null, (short) 0);
    }

    public boolean playlistContainsMovieId(Playlist playlist, Movie movie) {
        return playlist.getMovies().contains(movie);
    }
}
