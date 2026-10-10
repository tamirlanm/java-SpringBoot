package com.example.sis1.exception;

public class MangaNotFoundException extends RuntimeException {

    public MangaNotFoundException(Long id) {
        super("Manga with id " + id + " was not found");
    }
}