package com.erbeandroid.petfinder.feature.animal.type;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.erbeandroid.petfinder.core.data.model.Type;
import com.erbeandroid.petfinder.core.data.repository.Repository;
import com.erbeandroid.petfinder.feature.animal.common.State;
import com.erbeandroid.petfinder.feature.animal.common.StateData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class TypeViewModel extends ViewModel {

    private final Repository repository;
    private final CompositeDisposable compositeDisposable;

    private final StateData<List<Type>> _typeState = new StateData<>();
    public LiveData<State<List<Type>>> typeState = _typeState;

    @Inject
    public TypeViewModel(
        Repository repository,
        CompositeDisposable compositeDisposable
    ) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        this.getTypes();
    }

    public void getTypes() {
        compositeDisposable.add(
            repository.getTypes()
                .doOnSubscribe(__ ->
                    _typeState.postLoading()
                )
                .subscribe(
                    _typeState::postSuccess,
                    _typeState::postError
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}