package com.example.backend.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.backend.models.MovieModel;
import com.example.backend.repository.MovieRepository;

@Controller
public class AdminReportController {

    private final MovieRepository movieRepository;

    public AdminReportController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 📊 Top watched movies (by views)
    @GetMapping("/admin/reports")
    public String topWatchedMovies(Model model) {

        List<MovieModel> movies =
                movieRepository.findAllByOrderByViewsDesc();

        model.addAttribute("movies", movies);

        return "admin/reports/top-movies";
    }
}
