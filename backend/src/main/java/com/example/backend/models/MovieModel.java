package com.example.backend.models;

import jakarta.persistence.*;

@Entity
@Table(
    name = "movies",
    uniqueConstraints = @UniqueConstraint(columnNames = "movie_id")
)
public class MovieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String poster;

    private int views;

    @Column(nullable = false)
    private String movie; // video path

    public MovieModel() {}

    public MovieModel(String movieName, String description, String poster, int views, String movie) {
        this.movieName = movieName;
        this.description = description;
        this.poster = poster;
        this.views = views;
        this.movie = movie;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
