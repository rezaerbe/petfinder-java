package com.erbeandroid.petfinder.core.network.model;

import com.squareup.moshi.Json;

import java.util.List;

public class PaginatedAnimalResponse {
    @Json(name = "animals")
    private final List<AnimalResponse> animals;
    @Json(name = "pagination")
    private final PaginationResponse pagination;

    public PaginatedAnimalResponse(List<AnimalResponse> animals, PaginationResponse pagination) {
        this.animals = animals;
        this.pagination = pagination;
    }

    public List<AnimalResponse> getAnimals() {
        return animals;
    }

    public PaginationResponse getPagination() {
        return pagination;
    }
}