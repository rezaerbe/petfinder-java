package com.erbeandroid.petfinder.core.data.model;

import static com.erbeandroid.petfinder.core.network.model.BreedsResponse.BreedResponse;
import static com.erbeandroid.petfinder.core.network.model.TypesResponse.TypeResponse;

import com.erbeandroid.petfinder.core.network.model.AnimalResponse;

public class Mapper {

    public static Type typeToDomain(TypeResponse typeResponse) {
        return new Type(
            typeResponse.getName()
        );
    }

    public static Breed breedToDomain(BreedResponse breedResponse) {
        return new Breed(
            breedResponse.getName()
        );
    }

    public static Animal animalToDomain(AnimalResponse animalResponse) {
        return new Animal(
            animalResponse.getId(),
            animalResponse.getName()
        );
    }

    public static AnimalDetail animalDetailToDomain(AnimalResponse animalResponse) {
        return new AnimalDetail(
            animalResponse.getId(),
            animalResponse.getOrganizationId(),
            animalResponse.getUrl(),
            animalResponse.getType(),
            animalResponse.getSpecies(),
            animalResponse.getAge(),
            animalResponse.getGender(),
            animalResponse.getSize(),
            animalResponse.getCoat(),
            animalResponse.getName(),
            animalResponse.getDescription()
        );
    }
}