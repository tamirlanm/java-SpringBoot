package com.example.sis1.controller;

import com.example.sis1.dto.CreateMangaRequest;
import com.example.sis1.dto.MangaResponse;
import com.example.sis1.dto.UpdateMangaRequest;
import com.example.sis1.service.MangaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mangas")
public class MangaController {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @PostMapping
    public ResponseEntity<MangaResponse> create(
            @Valid @RequestBody CreateMangaRequest request
    ) {
        MangaResponse response = mangaService.create(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MangaResponse>> getAll() {
        return ResponseEntity.ok(mangaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MangaResponse> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(mangaService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MangaResponse> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateMangaRequest request
    ) {
        return ResponseEntity.ok(mangaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id
    ) {
        mangaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}