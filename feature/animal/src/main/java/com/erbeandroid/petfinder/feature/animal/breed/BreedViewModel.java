package com.erbeandroid.petfinder.feature.animal.breed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.erbeandroid.petfinder.core.data.model.Breed;
import com.erbeandroid.petfinder.core.data.repository.Repository;
import com.erbeandroid.petfinder.feature.animal.common.State;
import com.erbeandroid.petfinder.feature.animal.common.StateData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class BreedViewModel extends ViewModel {

    private final Repository repository;
    private final CompositeDisposable compositeDisposable;
    private final SavedStateHandle savedStateHandle;

    private final StateData<List<Breed>> _breedState = new StateData<>();
    public LiveData<State<List<Breed>>> breedState = _breedState;

    @Inject
    public BreedViewModel(
        Repository repository,
        CompositeDisposable compositeDisposable,
        SavedStateHandle savedStateHandle
    ) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        this.savedStateHandle = savedStateHandle;
        this.getBreeds();
    }

    public void getBreeds() {
        String type = savedStateHandle.get("type");
        compositeDisposable.add(
            repository.getBreeds(type)
                .doOnSubscribe(__ ->
                    _breedState.postLoading()
                )
                .subscribe(
                    _breedState::postSuccess,
                    _breedState::postError
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}