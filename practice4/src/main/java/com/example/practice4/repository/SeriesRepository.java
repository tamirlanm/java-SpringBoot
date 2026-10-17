package com.example.practice4.repository;

import com.example.practice4.entity.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SeriesRepository extends JpaRepository<Series,Long> {

    Page<Series> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("""
        SELECT s FROM Series s
        WHERE LOWER(s.genre) = LOWER(:genre)
        """)
    Page<Series> findByGenre(@Param("genre") String genre, Pageable pageable);
}