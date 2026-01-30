package com.example.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.backend.models.*;
import com.example.backend.repository.*;
import com.example.backend.service.CustomUserDetail;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin(origins = "*")
public class WatchlistController {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private MovieRepository movieRepository;

    // 1. Add movie to watchlist
    @PostMapping("/add")
    public String addToWatchlist(@RequestParam Long movieId) {

        System.out.println("WATCHLIST CONTROLLER HIT");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "Unauthorized";
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        System.out.println("LOGGED USER: " + user.getEmail());

        MovieModel movie = movieRepository.findById(movieId).orElse(null);

        if (movie == null) {
            return "Movie not found";
        }

        boolean exists = watchlistRepository.existsByUserAndMovie(user, movie);
        if (exists) {
            return "Movie already in watchlist";
        }

        WatchlistModel watchlist = new WatchlistModel(user, movie);
        watchlistRepository.save(watchlist);

        return "Movie added to watchlist";
    }

    // 2. Get my watchlist
    @GetMapping("/my")
    public List<WatchlistModel> getMyWatchlist() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return List.of();
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        return watchlistRepository.findByUser(user);
    }

    // 3. Remove from watchlist
    @DeleteMapping("/remove")
    public String removeFromWatchlist(@RequestParam Long movieId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "Unauthorized";
        }

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
        UserModel user = userDetails.getUser();

        List<WatchlistModel> list = watchlistRepository.findByUser(user);

        for (WatchlistModel wl : list) {
            if (wl.getMovie().getMovieId().equals(movieId)) {
                watchlistRepository.delete(wl);
                return "Movie removed from watchlist";
            }
        }

        return "Movie not found in watchlist";
    }
}
