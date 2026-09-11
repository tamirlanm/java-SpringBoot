package com.example.practice1.service;

import com.example.practice1.dto.MovieResponseDTO;
import com.example.practice1.dto.MovieRequestDTO;
import com.example.practice1.models.Movie;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class MovieServiceImplementation implements MovieService{
    private final List<Movie> textDatabase = new CopyOnWriteArrayList<>();

    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID());
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setDescription(movieRequestDTO.getDescription());
        movie.setPrice(movieRequestDTO.getPrice());

        textDatabase.add(movie);
        return mapToResponseDTO(movie);
    }

    @Override
    public List<MovieResponseDTO> getAllMovies() {
        return textDatabase.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public MovieResponseDTO getMovie(UUID id) {
        Movie movie = textDatabase.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);

        return  mapToResponseDTO(movie);
    }

    @Override
    public MovieResponseDTO updateMovie(UUID id, MovieRequestDTO movieRequestDTO) {
        Movie exists = textDatabase.stream().filter(m -> m.getId().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Movie with ID " + id + "not found."));

        exists.setTitle(movieRequestDTO.getTitle());
        exists.setDescription(movieRequestDTO.getDescription());
        exists.setPrice(movieRequestDTO.getPrice());
        return mapToResponseDTO(exists);
    }

    @Override
    public void deleteMovie(UUID id) {
        boolean removed = textDatabase.removeIf(m -> m.getId().equals(id));
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