package com.erbeandroid.petfinder.core.data.repository;

import androidx.paging.PagingData;
import androidx.paging.PagingDataTransforms;
import androidx.paging.rxjava3.PagingRx;

import com.erbeandroid.petfinder.core.data.model.Animal;
import com.erbeandroid.petfinder.core.data.model.AnimalDetail;
import com.erbeandroid.petfinder.core.data.model.Breed;
import com.erbeandroid.petfinder.core.data.model.Mapper;
import com.erbeandroid.petfinder.core.data.model.Type;
import com.erbeandroid.petfinder.core.network.datasource.RemoteDataSource;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class PetFinderRepository implements Repository {

    private final RemoteDataSource remoteDataSource;

    @Inject
    public PetFinderRepository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<Type>> getTypes() {
        return remoteDataSource.getTypes().map(typesResponse ->
            typesResponse.getTypes().stream().map(
                Mapper::typeToDomain
            ).collect(Collectors.toList())
        ).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Breed>> getBreeds(String type) {
        return remoteDataSource.getBreeds(type).map(breedsResponse ->
            breedsResponse.getBreeds().stream().map(
                Mapper::breedToDomain
            ).collect(Collectors.toList())
        ).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<PagingData<Animal>> getAnimals(String type, String breed) {
        return PagingRx.getFlowable(remoteDataSource.getAnimals(type, breed))
            .map(pagingData ->
                PagingDataTransforms.map(
                    pagingData,
                    Executors.newSingleThreadExecutor(),
                    Mapper::animalToDomain
                )
            );
    }

    @Override
    public Single<AnimalDetail> getAnimalDetail(Integer id) {
        return remoteDataSource.getAnimalDetail(id).map(animalDetailResponse ->
            Mapper.animalDetailToDomain(animalDetailResponse.getAnimal())
        ).observeOn(AndroidSchedulers.mainThread());
    }
}