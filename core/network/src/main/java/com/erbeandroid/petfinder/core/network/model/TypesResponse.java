package com.erbeandroid.petfinder.core.network.model;

import com.squareup.moshi.Json;

import java.util.List;

public class TypesResponse {
    @Json(name = "types")
    private final List<TypeResponse> types;

    public TypesResponse(List<TypeResponse> types) {
        this.types = types;
    }

    public List<TypeResponse> getTypes() {
        return types;
    }

    public static class TypeResponse {
        @Json(name = "name")
        private final String name;

        public TypeResponse(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}