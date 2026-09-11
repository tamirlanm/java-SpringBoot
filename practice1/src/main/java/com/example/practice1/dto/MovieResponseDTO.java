package com.example.practice1.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class MovieResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private Double price;
}