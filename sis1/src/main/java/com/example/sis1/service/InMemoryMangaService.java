package com.example.sis1.service;

import com.example.sis1.dto.CreateMangaRequest;
import com.example.sis1.dto.MangaResponse;
import com.example.sis1.dto.UpdateMangaRequest;
import com.example.sis1.exception.MangaNotFoundException;
import com.example.sis1.model.Manga;
import com.example.sis1.service.MangaService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryMangaService implements MangaService {

    private final Map<Long, Manga> storage = new HashMap<>();
    private long nextId = 1;

    @Override
    public synchronized MangaResponse create(CreateMangaRequest request) {
        Long id = nextId++;

        Manga manga = new Manga(
                id,
                request.title(),
                request.author(),
                request.description(),
                request.status(),
                request.chaptersCount()
        );

        storage.put(id, manga);

        return toResponse(manga);
    }

    @Override
    public synchronized List<MangaResponse> getAll() {
        return storage.values()
                .stream()
                .sorted(Comparator.comparing(Manga::getId))
                .map(this::toResponse)
                .toList();
    }

    @Override
    public synchronized MangaResponse getById(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public synchronized MangaResponse update(
            Long id,
            UpdateMangaRequest request
    ) {
        Manga manga = findById(id);

        manga.update(
                request.title(),
                request.author(),
                request.description(),
                request.status(),
                request.chaptersCount()
        );

        return toResponse(manga);
    }

    @Override
    public synchronized void delete(Long id) {
        Manga removed = storage.remove(id);

        if (removed == null) {
            throw new MangaNotFoundException(id);
        }
    }

    private Manga findById(Long id) {
        Manga manga = storage.get(id);

        if (manga == null) {
            throw new MangaNotFoundException(id);
        }

        return manga;
    }

    private MangaResponse toResponse(Manga manga) {
        return new MangaResponse(
                manga.getId(),
                manga.getTitle(),
                manga.getAuthor(),
                manga.getDescription(),
                manga.getStatus(),
                manga.getChaptersCount()
        );
    }
}