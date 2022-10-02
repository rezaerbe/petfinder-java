package com.erbeandroid.petfinder.core.network.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.erbeandroid.petfinder.core.network.model.AnimalResponse;
import com.erbeandroid.petfinder.core.network.model.PaginatedAnimalResponse;
import com.erbeandroid.petfinder.core.network.service.PetFinderService;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimalPagingDataSource extends RxPagingSource<Integer, AnimalResponse> {

    private final PetFinderService petFinderService;
    private final String type;
    private final String breed;

    public AnimalPagingDataSource(PetFinderService petFinderService, String type, String breed) {
        this.petFinderService = petFinderService;
        this.type = type;
        this.breed = breed;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, AnimalResponse>> loadSingle(@NonNull LoadParams<Integer> params) {
        Integer page = params.getKey() != null ? params.getKey() : 1;
        Integer limit = params.getLoadSize();

        return petFinderService.getAnimals(type, breed, page, limit)
            .subscribeOn(Schedulers.io())
            .map(result -> toLoadResult(result, page))
            .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, AnimalResponse> toLoadResult(
        PaginatedAnimalResponse response,
        Integer page
    ) {
        return new LoadResult.Page<>(
            response.getAnimals(),
            null,
            page.equals(response.getPagination().getTotalPages()) ? null : page + 1
        );
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, AnimalResponse> state) {
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, AnimalResponse> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }
}