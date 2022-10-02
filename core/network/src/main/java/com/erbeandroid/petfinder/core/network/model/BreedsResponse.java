package com.erbeandroid.petfinder.core.network.model;

import com.squareup.moshi.Json;

import java.util.List;

public class BreedsResponse {
    @Json(name = "breeds")
    private final List<BreedResponse> breeds;

    public BreedsResponse(List<BreedResponse> breeds) {
        this.breeds = breeds;
    }

    public List<BreedResponse> getBreeds() {
        return breeds;
    }

    public static class BreedResponse {
        @Json(name = "name")
        private final String name;

        public BreedResponse(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}