package com.moviesdb.movie.repository;

import com.moviesdb.movie.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {

    @Query("select mr " +
            "from MovieRating mr " +
            "where mr.id.movieId = :movieId " +
            "and mr.id.userId = :userId")
    Optional<MovieRating> findByMovieAndUserId(@Param("movieId") long movieId, @Param("userId") long userId);
}
