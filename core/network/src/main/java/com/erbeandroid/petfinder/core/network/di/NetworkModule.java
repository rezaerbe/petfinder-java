package com.erbeandroid.petfinder.core.network.di;

import static com.erbeandroid.petfinder.core.network.util.NetworkConstant.BASE_ENDPOINT;

import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerCollector;
import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.chuckerteam.chucker.api.RetentionManager;
import com.erbeandroid.petfinder.core.network.datasource.PetFinderRemoteDataSource;
import com.erbeandroid.petfinder.core.network.datasource.RemoteDataSource;
import com.erbeandroid.petfinder.core.network.interceptor.AuthenticationInterceptor;
import com.erbeandroid.petfinder.core.network.interceptor.NetworkStatusInterceptor;
import com.erbeandroid.petfinder.core.network.service.PetFinderService;
import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class NetworkModule {

    @Binds
    public abstract RemoteDataSource bindRemoteDataSource(
        PetFinderRemoteDataSource petFinderRemoteDataSource
    );

    @Provides
    @Singleton
    public static Moshi provideMoshi() {
        return new Moshi.Builder().build();
    }

    @Provides
    public static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    public static ChuckerCollector provideChuckCollector(
        @ApplicationContext Context context
    ) {
        return new ChuckerCollector(
            context,
            true,
            RetentionManager.Period.ONE_HOUR
        );
    }

    @Provides
    public static ChuckerInterceptor provideChuckInterceptor(
        @ApplicationContext Context context,
        ChuckerCollector chuckerCollector
    ) {
        return new ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250000L)
            .redactHeaders("Authorization", "Bearer")
            .alwaysReadResponseBody(false)
            .build();
    }

    @Provides
    public static OkHttpClient provideOkHttpClient(
        NetworkStatusInterceptor networkStatusInterceptor,
        AuthenticationInterceptor authenticationInterceptor,
        HttpLoggingInterceptor httpLoggingInterceptor,
        ChuckerInterceptor chuckInterceptor
    ) {
        return new OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(authenticationInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckInterceptor)
            .build();
    }

    @Provides
    public static Retrofit.Builder provideRetrofit(
        OkHttpClient okHttpClient
    ) {
        return new Retrofit.Builder()
            .baseUrl(BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create());
    }

    @Provides
    @Singleton
    public static PetFinderService providePetFinderService(
        Retrofit.Builder builder
    ) {
        return builder
            .build()
            .create(PetFinderService.class);
    }
}