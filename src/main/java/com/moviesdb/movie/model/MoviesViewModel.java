package com.moviesdb.movie.model;

import com.moviesdb.movie.model.Movie;

import java.util.List;

public record MoviesViewModel(List<Movie> movies,
                              int pageCount,
                              int pageNumber) {
}
