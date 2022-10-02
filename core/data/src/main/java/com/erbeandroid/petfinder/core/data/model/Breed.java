package com.erbeandroid.petfinder.core.data.model;

import androidx.annotation.NonNull;

public class Breed {

    private final String name;

    public Breed(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Breed{" +
            "name='" + name + '\'' +
            '}';
    }
}