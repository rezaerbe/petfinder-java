package com.erbeandroid.petfinder.core.preference.di;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.erbeandroid.petfinder.core.preference.datasource.PetFinderPreferenceDataSource;
import com.erbeandroid.petfinder.core.preference.datasource.PreferenceDataSource;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class PreferenceModule {

    @Binds
    public abstract PreferenceDataSource bindPreferenceDataSource(
        PetFinderPreferenceDataSource petFinderPreferenceDataSource
    );

    @Provides
    public static String provideMasterKeyAlias() {
        try {
            return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @Singleton
    public static SharedPreferences provideSharedPreferences(
        String masterKeyAlias,
        @ApplicationContext Context context
    ) {
        try {
            return EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}