package com.erbeandroid.petfinder.core.data.model;

import androidx.annotation.NonNull;

public class AnimalDetail {
    private final Integer id;
    private final String organizationId;
    private final String url;
    private final String type;
    private final String species;
    private final String age;
    private final String gender;
    private final String size;
    private final String coat;
    private final String name;
    private final String description;

    public AnimalDetail(
        Integer id,
        String organizationId,
        String url,
        String type,
        String species,
        String age,
        String gender,
        String size,
        String coat,
        String name,
        String description
    ) {
        this.id = id;
        this.organizationId = organizationId;
        this.url = url;
        this.type = type;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.coat = coat;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getSpecies() {
        return species;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getSize() {
        return size;
    }

    public String getCoat() {
        return coat;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String toString() {
        return "AnimalDetail{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}