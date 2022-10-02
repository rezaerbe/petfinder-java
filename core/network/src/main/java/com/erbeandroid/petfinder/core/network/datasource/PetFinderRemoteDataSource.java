package com.erbeandroid.petfinder.core.network.datasource;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import com.erbeandroid.petfinder.core.network.model.AnimalDetailResponse;
import com.erbeandroid.petfinder.core.network.model.AnimalResponse;
import com.erbeandroid.petfinder.core.network.model.BreedsResponse;
import com.erbeandroid.petfinder.core.network.model.TypesResponse;
import com.erbeandroid.petfinder.core.network.service.PetFinderService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PetFinderRemoteDataSource implements RemoteDataSource {

    private final PetFinderService petFinderService;

    @Inject
    public PetFinderRemoteDataSource(PetFinderService petFinderService) {
        this.petFinderService = petFinderService;
    }

    @Override
    public Observable<TypesResponse> getTypes() {
        return petFinderService.getTypes()
            .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BreedsResponse> getBreeds(String type) {
        return petFinderService.getBreeds(type)
            .subscribeOn(Schedulers.io());
    }

    @Override
    public Pager<Integer, AnimalResponse> getAnimals(String type, String breed) {
        return new Pager<>(
            new PagingConfig(20),
            () -> new AnimalPagingDataSource(petFinderService, type, breed)
        );
    }

    @Override
    public Single<AnimalDetailResponse> getAnimalDetail(Integer id) {
        return petFinderService.getAnimalDetail(id)
            .subscribeOn(Schedulers.io());
    }
}