package com.example.practice1.service;

import com.example.practice1.dto.MovieResponseDTO;
import com.example.practice1.dto.MovieRequestDTO;
import com.example.practice1.models.Movie;
import com.example.practice1.storage.MovieStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieServiceImplementation implements MovieService{
    private final MovieStorage movieStorage;

    public MovieServiceImplementation(MovieStorage movieStorage){
        this.movieStorage = movieStorage;
    }

    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setDescription(movieRequestDTO.getDescription());
        movie.setPrice(movieRequestDTO.getPrice());

        movieStorage.add(movie);
        return mapToResponseDTO(movie);
    }

    @Override
    public List<MovieResponseDTO> getAllMovies() {
        return movieStorage.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public MovieResponseDTO getMovie(UUID id) {
        Movie movie = movieStorage.findById(id);

        return  mapToResponseDTO(movie);
    }

    @Override
    public MovieResponseDTO updateMovie(UUID id, MovieRequestDTO movieRequestDTO) {
        Movie exists = movieStorage.findById(id);
        if(exists==null){
            throw new RuntimeException("Movie with ID " + id + " does not exist");
        }

        exists.setTitle(movieRequestDTO.getTitle());
        exists.setDescription(movieRequestDTO.getDescription());
        exists.setPrice(movieRequestDTO.getPrice());
        return mapToResponseDTO(exists);
    }

    @Override
    public void deleteMovie(UUID id) {
        boolean removed = movieStorage.deleteById(id);
        if(!removed) {
            throw new RuntimeException("Movie with ID" + id + "not found.");
        }
    }
    private MovieResponseDTO mapToResponseDTO(Movie movie) {
        MovieResponseDTO movieResponseDTO = new MovieResponseDTO();
        movieResponseDTO.setId(movie.getId());
        movieResponseDTO.setTitle(movie.getTitle());
        movieResponseDTO.setDescription(movie.getDescription());
        movieResponseDTO.setPrice(movie.getPrice());
        return movieResponseDTO;
    }
}