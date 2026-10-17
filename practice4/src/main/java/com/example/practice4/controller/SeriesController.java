package com.example.practice4.controller;

import org.springframework.web.bind.annotation.*;

import com.example.practice4.dto.SeriesResponse;
import com.example.practice4.service.SeriesService;
import org.springframework.data.domain.*;

@RestController 
@RequestMapping("/api/series")
public class SeriesController{
    private final SeriesService service;
    public SeriesController(SeriesService service){
        this.service = service;
    }

    @GetMapping
    public Page<SeriesResponse> getSeries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "title") String sortBy, @RequestParam(defaultValue = "asc") String direction, @RequestParam(required = false) String title, @RequestParam(required = false) String genre){
        return service.getSeries(page, size, sortBy, direction, title, genre);
    }
}