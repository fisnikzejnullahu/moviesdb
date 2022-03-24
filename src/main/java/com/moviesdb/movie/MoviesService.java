package com.moviesdb.movie;

import com.moviesdb.exception.NotFoundException;
import com.moviesdb.movie.model.*;
import com.moviesdb.movie.repository.MovieRatingRepository;
import com.moviesdb.movie.repository.MoviesRepository;
import com.moviesdb.playlist.Playlist;
import com.moviesdb.playlist.PlaylistsService;
import com.moviesdb.user.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class MoviesService {

    private final MoviesRepository moviesRepository;
    private final MovieRatingRepository movieRatingRepository;
    private final PlaylistsService playlistsService;

    @Value("${default.movies.response.page-size}")
    private int pageSize;

    public MoviesService(MoviesRepository moviesRepository, MovieRatingRepository movieRatingRepository, PlaylistsService playlistsService) {
        this.moviesRepository = moviesRepository;
        this.movieRatingRepository = movieRatingRepository;
        this.playlistsService = playlistsService;
    }

    public MoviesViewModel getMovies(int page, String genres) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        List<Movie> movies;
        int totalPages;

        if (genres == null || genres.isEmpty()) {
            movies = moviesRepository
                    .findByPopularityWithPagination(pageable);
            totalPages = 1 + (int) (moviesRepository.count() / pageSize);
        }
        else {
            var list = List.of(genres.split(","));
            Page<Movie> byGenresName = moviesRepository
                    .findDistinctByGenresNameInOrderByRateDesc(list, pageable);
            movies = byGenresName.getContent();
            totalPages = byGenresName.getTotalPages();
        }

        return new MoviesViewModel(movies, totalPages, page);
    }

    private Movie getMovie(long id) {
        return moviesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public MovieDetailsViewModel getMovieDetails(long movieId) {
        Movie movie = getMovie(movieId);

        if (UsersService.isUserLoggedIn()) {
            List<Playlist> playlistsOfLoggedInUser = playlistsService.getPlaylistsOfLoggedInUser();
            AtomicReference<Short> userRate = new AtomicReference<>((short) 0);

            movieRatingRepository.findByMovieAndUserId(movieId,
                            UsersService.getLoggedInUser().getId())
                    .ifPresent(movieRating -> {
                userRate.set(movieRating.getRate());
            });

            return new MovieDetailsViewModel(movie, playlistsOfLoggedInUser, userRate.get());
        }
        return new MovieDetailsViewModel(movie);
    }

    public void addMovieRate(long movieId, short rate) {
        var user = UsersService.getLoggedInUser();

        var movieRate = new MovieRating();
        movieRate.setRate(rate);
        movieRate.setId(new MovieRatingId(movieId, user.getId()));

        movieRatingRepository.save(movieRate);
    }
}
