package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.backend.models.MovieModel;
import com.example.backend.repository.MovieRepository;
import java.util.List;


@RestController
@RequestMapping("/api/movies")
public class MovieController{
	
		@Autowired
	private MovieRepository movieRepository;
	
	// 1. Get all movies
    @GetMapping
    public List<MovieModel> getAllMovies() {
        return movieRepository.findAll();
    }

    // 2. Get movie by id
    @GetMapping("/{id}")
    public MovieModel getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id).orElse(null);
    }
}