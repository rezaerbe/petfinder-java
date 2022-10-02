package com.erbeandroid.petfinder.feature.animal.common;

import androidx.lifecycle.MutableLiveData;

public class StateData<T> extends MutableLiveData<State<T>> {

    public void postSuccess(T data) {
        postValue(new State<>(State.Status.SUCCESS, data, null));
    }

    public void postError(Throwable throwable) {
        postValue(new State<>(State.Status.ERROR, null, throwable));
    }

    public void postLoading() {
        postValue(new State<>(State.Status.LOADING, null, null));
    }
}