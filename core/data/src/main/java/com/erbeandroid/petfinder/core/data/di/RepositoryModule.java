package com.erbeandroid.petfinder.core.data.di;

import com.erbeandroid.petfinder.core.data.repository.PetFinderRepository;
import com.erbeandroid.petfinder.core.data.repository.Repository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    public abstract Repository bindRepository(
        PetFinderRepository petFinderRepository
    );

    @Provides
    public static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}