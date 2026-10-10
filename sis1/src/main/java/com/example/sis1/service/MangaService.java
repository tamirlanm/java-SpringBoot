package com.example.sis1.service;

import com.example.sis1.dto.CreateMangaRequest;
import com.example.sis1.dto.MangaResponse;
import com.example.sis1.dto.UpdateMangaRequest;

import java.util.List;

public interface MangaService {

    MangaResponse create(CreateMangaRequest request);

    List<MangaResponse> getAll();

    MangaResponse getById(Long id);

    MangaResponse update(Long id, UpdateMangaRequest request);

    void delete(Long id);
}