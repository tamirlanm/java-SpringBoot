package com.example.practice4.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @OneToMany(mappedBy = "series", fetch = FetchType.LAZY)
    private List<Episode> episodes = new ArrayList<>();

    protected Series() {}

    public Series(String title, String genre, Integer releaseYear){
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }
    public Long getId() { return id;}
    public String getTitle() { return title;}
    public  String getGenre() { return genre; }
    public Integer getReleaseYear() { return releaseYear; }
    public List<Episode> getEpisodes(){
        return episodes;
    }
}