package com.example.practice1.storage;

import com.example.practice1.models.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MovieStorage{
    private final List<Movie> movies = new CopyOnWriteArrayList<>();
    public void add(Movie movie){
        movies.add(movie);
    }
    public List<Movie> findAll(){
        return movies;
    }
    public Movie findById(UUID id){
        return movies.stream().filter(movie -> movie.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean deleteById(UUID id){
        return movies.removeIf(movie -> movie.getId().equals(id));
    }
}