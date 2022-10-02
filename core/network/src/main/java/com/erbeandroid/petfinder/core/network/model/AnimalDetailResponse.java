package com.erbeandroid.petfinder.core.network.model;

import com.squareup.moshi.Json;

public class AnimalDetailResponse {
    @Json(name = "animal")
    private final AnimalResponse animal;

    public AnimalDetailResponse(AnimalResponse animal) {
        this.animal = animal;
    }

    public AnimalResponse getAnimal() {
        return animal;
    }
}