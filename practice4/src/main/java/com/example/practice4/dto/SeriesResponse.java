package com.example.practice4.dto;

public record SeriesResponse(
    Long id,
    String title,
    String genre,
    Integer releaseYear
){}