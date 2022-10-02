package com.erbeandroid.petfinder.core.data.model;

import androidx.annotation.NonNull;

public class Type {

    private final String name;

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Type{" +
            "name='" + name + '\'' +
            '}';
    }
}