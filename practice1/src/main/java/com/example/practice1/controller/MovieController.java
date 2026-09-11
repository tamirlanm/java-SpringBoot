package com.example.practice1.controller;

import com.example.practice1.dto.MovieResponseDTO;
import com.example.practice1.dto.MovieRequestDTO;
import com.example.practice1.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO createdMovie = movieService.createMovie(movieRequestDTO);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
        List<MovieResponseDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovie(@PathVariable UUID id) {
        MovieResponseDTO movie = movieService.getMovie(id);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable UUID id, @RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO updatedMovie = movieService.updateMovie(id, movieRequestDTO);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> deleteMovie(@PathVariable UUID id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}