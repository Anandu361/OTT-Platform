package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.*;

public interface WatchHistoryRepository extends JpaRepository<WatchHistoryModel, Long> {

    // 🔹 Get full watch history (latest first)
    List<WatchHistoryModel> findByUserOrderByWatchedAtDesc(UserModel user);

    // 🔹 Find existing entry for user + movie (for update logic)
    Optional<WatchHistoryModel> findByUserAndMovie(UserModel user, MovieModel movie);

    // 🔹 Get latest 10 watched movies
    List<WatchHistoryModel> findTop10ByUserOrderByWatchedAtDesc(UserModel user);
    void deleteByMovie(MovieModel movie);

}
