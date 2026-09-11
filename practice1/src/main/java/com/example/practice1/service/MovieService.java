package  com.example.practice1.service;
import com.example.practice1.dto.MovieRequestDTO;
import com.example.practice1.dto.MovieResponseDTO;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO);
    List<MovieResponseDTO> getAllMovies();
    MovieResponseDTO getMovie(UUID id);
    MovieResponseDTO updateMovie(UUID id, MovieRequestDTO movieRequestDTO);
    void deleteMovie(UUID id);
}