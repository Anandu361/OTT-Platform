package com.example.backend.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.models.MovieModel;
import com.example.backend.repository.MovieRepository;
import com.example.backend.repository.WatchHistoryRepository;
import com.example.backend.repository.WatchlistRepository;

@Controller
@RequestMapping("/admin/movies")
public class AdminMovieController {

    private final MovieRepository movieRepository;
    
    @Autowired
    private  WatchlistRepository watchlistRepository;
    
    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    public AdminMovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 📃 List movies
    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/movies/movies";
    }

    // ➕ Add movie form
    @GetMapping("/add")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new MovieModel());
        return "admin/movies/add";
    }

    // 💾 Save new movie
    @PostMapping("/add")
    public String saveMovie(
            @RequestParam String movieName,
            @RequestParam String description,
            @RequestParam("posterFile") MultipartFile posterFile,
            @RequestParam("videoFile") MultipartFile videoFile
    ) throws IOException {

        // 🔹 Generate safe unique filenames
        String posterName = System.currentTimeMillis() + "_" + posterFile.getOriginalFilename();
        String videoName  = System.currentTimeMillis() + "_" + videoFile.getOriginalFilename();

        // 🔹 Upload paths (STATIC FOLDER)
        Path posterPath = Paths.get("src/main/resources/static/posters/", posterName);
        Path videoPath  = Paths.get("src/main/resources/static/videos/", videoName);

        // 🔹 Save files
        Files.copy(posterFile.getInputStream(), posterPath);
        Files.copy(videoFile.getInputStream(), videoPath);

        // 🔹 Save movie in DB
        MovieModel movie = new MovieModel();
        movie.setMovieName(movieName);
        movie.setDescription(description);
        movie.setPoster("/posters/" + posterName); // 👈 PATH stored
        movie.setMovie("/videos/" + videoName);    // 👈 PATH stored
        movie.setViews(0);

        movieRepository.save(movie);

        return "redirect:/admin/movies";
    }


    // ✏ Edit movie form
    @GetMapping("/edit/{id}")
    public String editMovieForm(@PathVariable Long id, Model model) {
        MovieModel movie = movieRepository.findById(id).orElseThrow();
        model.addAttribute("movie", movie);
        return "admin/movies/edit";
    }

    // 💾 Update movie
    @PostMapping("/edit")
    public String updateMovie(
            @RequestParam Long movieId,
            @RequestParam String movieName,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile posterFile,
            @RequestParam(required = false) MultipartFile videoFile
    ) throws IOException {

        MovieModel movie = movieRepository.findById(movieId).orElseThrow();

        movie.setMovieName(movieName);
        movie.setDescription(description);

        // 🔄 Replace poster if new one uploaded
        if (posterFile != null && !posterFile.isEmpty()) {

            // delete old poster
            deleteStaticFile(movie.getPoster());

            String posterName = System.currentTimeMillis() + "_" + posterFile.getOriginalFilename();
            Path posterPath = Paths.get("src/main/resources/static/posters/", posterName);
            Files.copy(posterFile.getInputStream(), posterPath);

            movie.setPoster("/posters/" + posterName);
        }

        // 🔄 Replace video if new one uploaded
        if (videoFile != null && !videoFile.isEmpty()) {

            // delete old video
            deleteStaticFile(movie.getMovie());

            String videoName = System.currentTimeMillis() + "_" + videoFile.getOriginalFilename();
            Path videoPath = Paths.get("src/main/resources/static/videos/", videoName);
            Files.copy(videoFile.getInputStream(), videoPath);

            movie.setMovie("/videos/" + videoName);
        }

        movieRepository.save(movie);
        return "redirect:/admin/movies";
    }


    private void deleteStaticFile(String path) throws IOException {
        if (path == null) return;

        Path filePath = Paths.get("src/main/resources/static", path);
        Files.deleteIfExists(filePath);
    }


	// ❌ Delete movie
    @Transactional
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) throws IOException {

        MovieModel movie = movieRepository.findById(id).orElseThrow();

        // 1️⃣ Remove from watchlist
        watchlistRepository.deleteByMovie(movie);

        // 2️⃣ Remove from watch history
        watchHistoryRepository.deleteByMovie(movie);

        // 3️⃣ Delete files
        deleteStaticFile(movie.getPoster());
        deleteStaticFile(movie.getMovie());

        // 4️⃣ Delete movie
        movieRepository.delete(movie);

        return "redirect:/admin/movies";
    }


}
