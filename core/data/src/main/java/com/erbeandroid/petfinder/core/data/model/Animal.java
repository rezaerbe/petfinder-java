package com.erbeandroid.petfinder.core.data.model;

public class Animal {
    private final Integer id;
    private final String name;

    public Animal(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}