package com.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="movies", uniqueConstraints = @UniqueConstraint(columnNames = "movie_id"))
public class MovieModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long movie_id;
	
	private String movie_name;
	private String description;
	private String poster;
	private int views;
	private String movie;
	
	public MovieModel(String movie_name, String description, String poster, int views, String movie) {
		this.setMovie_name(movie_name);
		this.setDescription(description);
		this.setPoster(poster);
		this.setViews(views);
		this.setMovie(movie);
		
	}
	public MovieModel() {
		super();
	}
	public Long getMovie_id() {
		return movie_id;
	}
	public String getMovie_name() {
		return movie_name;
	}
	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String descrition) {
		this.description = descrition;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getMovie() {
		return movie;
	}
	public void setMovie(String movie) {
		this.movie = movie;
	}
}