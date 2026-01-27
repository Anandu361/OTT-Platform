package com.example.backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "watch_history")
public class WatchHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK → users
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    // FK → movies
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieModel movie;

    private LocalDateTime watchedAt;

    public WatchHistoryModel() {}

    public WatchHistoryModel(UserModel user, MovieModel movie) {
        this.user = user;
        this.movie = movie;
        this.watchedAt = LocalDateTime.now();
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

    public LocalDateTime getWatchedAt() {
        return watchedAt;
    }

    public void setWatchedAt(LocalDateTime watchedAt) {
        this.watchedAt = watchedAt;
    }
}
