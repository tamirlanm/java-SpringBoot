package com.example.sis1.dto;

import com.example.sis1.model.MangaStatus;
import jakarta.validation.constraints.*;

public record UpdateMangaRequest(

        @NotBlank(message = "Title must not be blank")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        String title,

        @NotBlank(message = "Author must not be blank")
        @Size(max = 100, message = "Author must not exceed 100 characters")
        String author,

        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        @NotNull(message = "Status is required")
        MangaStatus status,

        @NotNull(message = "Chapters count is required")
        @PositiveOrZero(message = "Chapters count must be zero or greater")
        Integer chaptersCount

) {}