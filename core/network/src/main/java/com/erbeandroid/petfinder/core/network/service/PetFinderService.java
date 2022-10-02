package com.erbeandroid.petfinder.core.network.service;

import com.erbeandroid.petfinder.core.network.model.AnimalDetailResponse;
import com.erbeandroid.petfinder.core.network.model.BreedsResponse;
import com.erbeandroid.petfinder.core.network.model.PaginatedAnimalResponse;
import com.erbeandroid.petfinder.core.network.model.TypesResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PetFinderService {

    @GET("types")
    Observable<TypesResponse> getTypes();

    @GET("types/{type}/breeds")
    Observable<BreedsResponse> getBreeds(
        @Path("type") String type
    );

    @GET("animals")
    Single<PaginatedAnimalResponse> getAnimals(
        @Query("type") String type,
        @Query("breed") String breed,
        @Query("page") Integer page,
        @Query("limit") Integer limit
    );

    @GET("animals/{id}")
    Single<AnimalDetailResponse> getAnimalDetail(
        @Path("id") Integer id
    );
}