package com.example.practice4.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "episode_number", nullable = false)
    private Integer episodeNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    protected Episode() {
    }

    public Long getId(){ return id;}

    public String  getTitle() {
        return title;
    }
    public Integer getEpisodeNumber() {
        return episodeNumber;
    }
}