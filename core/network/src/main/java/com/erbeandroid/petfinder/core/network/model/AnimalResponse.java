package com.erbeandroid.petfinder.core.network.model;

import com.squareup.moshi.Json;

public class AnimalResponse {
    @Json(name = "id")
    private final Integer id;
    @Json(name = "organization_id")
    private final String organizationId;
    @Json(name = "url")
    private final String url;
    @Json(name = "type")
    private final String type;
    @Json(name = "species")
    private final String species;
    @Json(name = "age")
    private final String age;
    @Json(name = "gender")
    private final String gender;
    @Json(name = "size")
    private final String size;
    @Json(name = "coat")
    private final String coat;
    @Json(name = "name")
    private final String name;
    @Json(name = "description")
    private final String description;

    public AnimalResponse(
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
}