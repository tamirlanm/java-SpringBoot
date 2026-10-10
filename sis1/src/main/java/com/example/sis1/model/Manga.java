package com.example.sis1.model;
public class Manga {

    private final Long id;
    private String title;
    private String author;
    private String description;
    private MangaStatus status;
    private Integer chaptersCount;

    public Manga(
            Long id,
            String title,
            String author,
            String description,
            MangaStatus status,
            Integer chaptersCount
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.status = status;
        this.chaptersCount = chaptersCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public MangaStatus getStatus() {
        return status;
    }

    public Integer getChaptersCount() {
        return chaptersCount;
    }

    public void update(
            String title,
            String author,
            String description,
            MangaStatus status,
            Integer chaptersCount
    ) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.status = status;
        this.chaptersCount = chaptersCount;
    }
}