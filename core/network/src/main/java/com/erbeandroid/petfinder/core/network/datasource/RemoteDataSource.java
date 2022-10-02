package com.erbeandroid.petfinder.core.network.datasource;

import androidx.paging.Pager;

import com.erbeandroid.petfinder.core.network.model.AnimalDetailResponse;
import com.erbeandroid.petfinder.core.network.model.AnimalResponse;
import com.erbeandroid.petfinder.core.network.model.BreedsResponse;
import com.erbeandroid.petfinder.core.network.model.TypesResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface RemoteDataSource {

    Observable<TypesResponse> getTypes();

    Observable<BreedsResponse> getBreeds(String type);

    Pager<Integer, AnimalResponse> getAnimals(String type, String breed);

    Single<AnimalDetailResponse> getAnimalDetail(Integer id);
}