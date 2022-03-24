package com.moviesdb.movie.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moviesdb.genre.Genre;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Movie {

    @Id
    private long id;
    private String title;
    private String description;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    private String director;

    @Column(name = "release_date")

    @JsonFormat(pattern = "dd MMM yyyy")
    private LocalDate releaseDate;

    @JsonIgnore
    @Column(name = "duration_iso")  //iso format 8601
    private String durationIsoFormat;

    @OneToMany(mappedBy = "movie")
    private List<MovieActor> actors;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @Formula("(select avg(r.rating) from movie_rating r where r.movie_id = id)")
    private String rate;

    @Column(name = "trailer_youtube_video_id")
    private String trailerYoutubeVideoId;

    public Movie() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDurationIsoFormat() {
        return durationIsoFormat;
    }

    public void setDurationIsoFormat(String duration) {
        this.durationIsoFormat = duration;
    }

    public String getRate() {
        return rate != null ? String.format("%.1f", Double.parseDouble(rate)) : "0";
    }

    public void setRate(String rating) {
        this.rate = rating;
    }

    @JsonProperty
    public String getDuration() {
        Duration parse = Duration.parse(durationIsoFormat);
        long hours = parse.toHours();
        return (hours != 0)
                ? hours + "H " + parse.toMinutesPart() + "M"
                : parse.toMinutesPart() + "M";
    }

    public List<MovieActor> getActors() {
        return actors;
    }

    public void setActors(List<MovieActor> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getTrailerYoutubeVideoId() {
        return trailerYoutubeVideoId;
    }

    public void setTrailerYoutubeVideoId(String trailerYoutubeVideoId) {
        this.trailerYoutubeVideoId = trailerYoutubeVideoId;
    }
}
