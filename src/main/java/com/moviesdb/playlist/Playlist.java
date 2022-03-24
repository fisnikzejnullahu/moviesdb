package com.moviesdb.playlist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moviesdb.movie.model.Movie;
import com.moviesdb.user.UserAccount;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @SequenceGenerator(name="playlist_id_seq",
            sequenceName="playlist_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="playlist_id_seq")
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    @Column(name = "last_updated")
    @JsonFormat(pattern = "dd MMM yyyy")
    private LocalDate lastUpdated;

    @ManyToMany
    @JoinTable(name = "playlist_movie",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Movie> movies;

    public Playlist() {
    }

    public Playlist(String name) {
        this.name = name;
        this.movies = new ArrayList<>();
        this.lastUpdated = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
