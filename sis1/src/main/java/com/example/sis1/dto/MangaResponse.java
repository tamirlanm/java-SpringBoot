package com.example.sis1.dto;

import com.example.sis1.model.MangaStatus;

public record MangaResponse(
        Long id,
        String title,
        String author,
        String description,
        MangaStatus status,
        Integer chaptersCount
) {}