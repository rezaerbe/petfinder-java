package com.erbeandroid.petfinder.feature.animal.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.erbeandroid.petfinder.core.data.model.AnimalDetail;
import com.erbeandroid.petfinder.core.data.repository.Repository;
import com.erbeandroid.petfinder.feature.animal.common.State;
import com.erbeandroid.petfinder.feature.animal.common.StateData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class DetailViewModel extends ViewModel {

    private final Repository repository;
    private final CompositeDisposable compositeDisposable;
    private final SavedStateHandle savedStateHandle;

    private final StateData<AnimalDetail> _animalDetailState = new StateData<>();
    public LiveData<State<AnimalDetail>> animalDetailState = _animalDetailState;

    @Inject
    public DetailViewModel(
        Repository repository,
        CompositeDisposable compositeDisposable,
        SavedStateHandle savedStateHandle
    ) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        this.savedStateHandle = savedStateHandle;
        this.getAnimalDetail();
    }

    private void getAnimalDetail() {
        Integer id = savedStateHandle.get("id");
        compositeDisposable.add(
            repository.getAnimalDetail(id)
                .doOnSubscribe(__ ->
                    _animalDetailState.postLoading()
                )
                .subscribe(
                    _animalDetailState::postSuccess,
                    _animalDetailState::postError
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}