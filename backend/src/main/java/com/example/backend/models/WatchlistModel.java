package com.example.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "watchlist",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "movie_id"}))
public class WatchlistModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many watchlist rows → one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    // Many watchlist rows → one movie
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieModel movie;

    public WatchlistModel() {}

    public WatchlistModel(UserModel user, MovieModel movie) {
        this.user = user;
        this.movie = movie;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public MovieModel getMovie() {
        return movie;
    }

    public void setMovie(MovieModel movie) {
        this.movie = movie;
    }
}
