package com.example.backend.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.backend.models.UserModel;
import com.example.backend.repository.MovieRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.WatchlistRepository;
import com.example.backend.service.CustomUserDetail;
import com.example.backend.models.Role;

@Controller
public class AdminAuthController {
	
	private final UserRepository userRepository;
	private final MovieRepository movieRepository;
	private final WatchlistRepository watchlistRepository;
	
	public AdminAuthController(UserRepository userRepository, MovieRepository movieRepository, WatchlistRepository watchlistRepository) {
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
		this.watchlistRepository = watchlistRepository;
	}


    // Admin login page
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }

    // Admin dashboard
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        long totalUsers = userRepository.count();
        long totalMovies = movieRepository.count();
        long totalViews = movieRepository.getTotalViews();
        long totalWatchlist = watchlistRepository.count();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalMovies", totalMovies);
        model.addAttribute("totalViews", totalViews);
        model.addAttribute("totalWatchlist", totalWatchlist);

        return "admin/dashboard";
    }


    // Access denied page (optional but clean)
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
