package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backend.models.MovieModel;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, Long>{
	List<MovieModel> findByMovieNameContainingIgnoreCase(String movieName);
}