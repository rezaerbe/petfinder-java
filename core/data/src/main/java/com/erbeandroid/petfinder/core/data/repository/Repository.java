package com.erbeandroid.petfinder.core.data.repository;

import androidx.paging.PagingData;

import com.erbeandroid.petfinder.core.data.model.Animal;
import com.erbeandroid.petfinder.core.data.model.AnimalDetail;
import com.erbeandroid.petfinder.core.data.model.Breed;
import com.erbeandroid.petfinder.core.data.model.Type;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface Repository {

    Observable<List<Type>> getTypes();

    Observable<List<Breed>> getBreeds(String type);

    Flowable<PagingData<Animal>> getAnimals(String type, String breed);

    Single<AnimalDetail> getAnimalDetail(Integer id);
}