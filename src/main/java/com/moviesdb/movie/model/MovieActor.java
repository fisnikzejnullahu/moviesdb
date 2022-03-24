package com.moviesdb.movie.model;

import com.moviesdb.actor.Actor;

import javax.persistence.*;

@Entity
@Table(name = "movie_actor")
public class MovieActor {

    @EmbeddedId
    private MovieActorId id;

    @ManyToOne
    @MapsId("actor_id")
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "character_name")
    private String characterName;

    public MovieActorId getId() {
        return id;
    }

    public void setId(MovieActorId id) {
        this.id = id;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
