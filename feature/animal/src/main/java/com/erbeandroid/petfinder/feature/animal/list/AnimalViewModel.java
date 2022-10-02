package com.erbeandroid.petfinder.feature.animal.list;

import android.annotation.SuppressLint;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.erbeandroid.petfinder.core.data.model.Animal;
import com.erbeandroid.petfinder.core.data.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

@HiltViewModel
public class AnimalViewModel extends ViewModel {

    private final Repository repository;
    private final SavedStateHandle savedStateHandle;

    Flowable<PagingData<Animal>> flowable;

    @Inject
    public AnimalViewModel(
        Repository repository,
        SavedStateHandle savedStateHandle
    ) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        this.getAnimals();
    }

    @SuppressLint("UnsafeOptInUsageWarning")
    private void getAnimals() {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        String type = savedStateHandle.get("type");
        String breed = savedStateHandle.get("breed");
        flowable = repository.getAnimals(type, breed);
        PagingRx.cachedIn(flowable, viewModelScope);
    }
}