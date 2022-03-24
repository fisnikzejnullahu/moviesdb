package com.moviesdb.actor;

import com.moviesdb.movie.model.MovieActor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
public class Actor {

    @Id
    private long id;

    @Column
    private String name;
    @Column(name = "picture_url")
    private String pictureUrl;

    @Transient
    private List<MovieActor> movies;

    @Formula("(select count(*) from movie_actor ma where ma.actor_id = id)")
    private int moviesCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<MovieActor> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieActor> movies) {
        this.movies = movies;
    }

    public int getMoviesCount() {
        return moviesCount;
    }

    public void setMoviesCount(int moviesPlayed) {
        this.moviesCount = moviesPlayed;
    }
}
