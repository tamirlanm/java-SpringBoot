package com.example.practice4.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.practice4.dto.SeriesRequest;
import com.example.practice4.dto.SeriesResponse;
import com.example.practice4.entity.Series;
import com.example.practice4.repository.SeriesRepository;


import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service 
public class SeriesService{
    private final SeriesRepository repository;

    public SeriesService(SeriesRepository repository){
        this.repository = repository;
    }

    @Transactional
    public SeriesResponse create(SeriesRequest request){
        Series series = new Series(
            request.title(),
            request.genre(),
            request.releaseYear()
        );

        Series saved = repository.save(series);

        return new SeriesResponse(
            saved.getId(),
            saved.getTitle(),
            saved.getGenre(),
            saved.getReleaseYear()
        );
    }


    @Transactional(readOnly = true)
    public Page<SeriesResponse> getSeries(int page, int size, String sortBy, String direction, String title, String genre){
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(Math.max(size, 1), 20);

        Set<String> allowedSorts = Set.of("id", "title", "genre", "releaseYear");

        String safeSortBy = allowedSorts.contains(sortBy) ? sortBy : "title";
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(sortDirection, safeSortBy));

        Page<Series> result;

        if(title != null && !title.isBlank()) {
            result = repository.findByTitleContainingIgnoreCase(title, pageable);
        }
        else if( genre != null && !genre.isBlank()){
            result = repository.findByGenre(genre, pageable);
        }else{
            result  = repository.findAll(pageable);
        }

        return result.map(series -> new SeriesResponse(
            series.getId(),
            series.getTitle(),
            series.getGenre(),
            series.getReleaseYear()
        ));
    }
}