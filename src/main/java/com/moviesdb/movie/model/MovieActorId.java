package com.moviesdb.movie.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MovieActorId implements Serializable {

    @Column(name = "movie_id")
    private long movieId;
    @Column(name = "actor_id")
    private long actorId;

    public MovieActorId() {
    }

    public MovieActorId(long movieId, long actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getActorId() {
        return actorId;
    }

    public void setActorId(long actorId) {
        this.actorId = actorId;
    }

    @Override
    public String toString() {
        return "MovieActorId{" +
                "movieId=" + movieId +
                ", actorId=" + actorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieActorId that = (MovieActorId) o;

        if (movieId != that.movieId) return false;
        return actorId == that.actorId;
    }

    @Override
    public int hashCode() {
        int result = (int) (movieId ^ (movieId >>> 32));
        result = 31 * result + (int) (actorId ^ (actorId >>> 32));
        return result;
    }
}
