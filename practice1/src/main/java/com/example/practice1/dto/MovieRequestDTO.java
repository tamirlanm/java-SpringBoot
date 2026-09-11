package  com.example.practice1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRequestDTO {
    private String title;
    private String description;
    private Double price;
}