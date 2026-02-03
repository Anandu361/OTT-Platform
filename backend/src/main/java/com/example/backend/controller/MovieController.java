package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.backend.models.MovieModel;
import com.example.backend.repository.MovieRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;



@RestController
@RequestMapping("/api/movies")
public class MovieController{
	
		@Autowired
	private MovieRepository movieRepository;
	
	// 1. Get all movies
		@GetMapping
		public Page<MovieModel> getMovies(
		        @RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "8") int size
		) {
		    return movieRepository.findAll(PageRequest.of(page, size));
		}


    // 2. Get movie by id
    @GetMapping("/{id}")
    public MovieModel getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id).orElse(null);
    }
    
    @GetMapping("/search")
    public List<MovieModel> searchMovies(@RequestParam String query) {
        return movieRepository.findByMovieNameContainingIgnoreCase(query);
    }
}