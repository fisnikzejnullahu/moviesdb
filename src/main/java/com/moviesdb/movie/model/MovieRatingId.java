package com.moviesdb.movie.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MovieRatingId implements Serializable {

    @Column(name = "movie_id")
    private long movieId;
    @Column(name = "user_id")
    private long userId;

    public MovieRatingId() {
    }

    public MovieRatingId(long movieId, long userId) {
        this.movieId = movieId;
        this.userId = userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long actorId) {
        this.userId = actorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieRatingId that = (MovieRatingId) o;

        if (movieId != that.movieId) return false;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = (int) (movieId ^ (movieId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }
}
