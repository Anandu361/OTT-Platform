package com.example.backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.backend.models.*;
import com.example.backend.repository.*;
import com.example.backend.service.CustomUserDetail;

@RestController
@RequestMapping("/api/watch")
@CrossOrigin(origins = "*")
public class WatchController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    // 🎬 WATCH NOW (play movie + save/update history)
    @GetMapping("/now")
    public MovieModel watchNow(@RequestParam Long movieId) {

        // 1. Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        // 2. Get movie
        MovieModel movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return null;
        }

        // ✅ 3. INCREMENT VIEW COUNT (THIS WAS MISSING)
        movie.setViews(movie.getViews() + 1);
        movieRepository.save(movie);

        // 4. Save / update watch history (NO DUPLICATES)
        Optional<WatchHistoryModel> existing =
                watchHistoryRepository.findByUserAndMovie(user, movie);

        if (existing.isPresent()) {
            WatchHistoryModel h = existing.get();
            h.setWatchedAt(LocalDateTime.now());
            watchHistoryRepository.save(h);
        } else {
            WatchHistoryModel history = new WatchHistoryModel();
            history.setUser(user);
            history.setMovie(movie);
            history.setWatchedAt(LocalDateTime.now());
            watchHistoryRepository.save(history);
        }

        // 5. Return movie to frontend
        return movie;
    }


    // 🕒 GET WATCH HISTORY (latest first)
    @GetMapping("/history")
    public List<WatchHistoryModel> myHistory() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return List.of();
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        return watchHistoryRepository.findByUserOrderByWatchedAtDesc(user);
    }
}
