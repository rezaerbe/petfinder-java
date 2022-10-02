package com.erbeandroid.petfinder.feature.animal.common;

public class State<T> {

    private final Status status;
    private final T data;
    private final Throwable throwable;

    public State(Status status, T data, Throwable throwable) {
        this.status = status;
        this.data = data;
        this.throwable = throwable;
    }

    public T getData() {
        return data;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    public Boolean isError() {
        return status == Status.ERROR;
    }

    public Boolean isLoading() {
        return status == Status.LOADING;
    }

    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}