package com.moviesdb.movie.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "movie_rating")
public class MovieRating {

    @EmbeddedId
    private MovieRatingId id;

    @Column(name = "rating")
    private short rate;

    public MovieRatingId getId() {
        return id;
    }

    public void setId(MovieRatingId id) {
        this.id = id;
    }

    public short getRate() {
        return rate;
    }

    public void setRate(short rate) {
        this.rate = rate;
    }
}
