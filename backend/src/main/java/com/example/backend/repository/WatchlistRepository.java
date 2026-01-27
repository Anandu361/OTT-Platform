package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.WatchlistModel;
import com.example.backend.models.UserModel;

public interface WatchlistRepository extends JpaRepository<WatchlistModel, Long> {

    // Get all watchlist movies for a user
    List<WatchlistModel> findByUser(UserModel user);

    // Check if movie already in watchlist
    boolean existsByUserAndMovie(UserModel user, com.example.backend.models.MovieModel movie);
}
