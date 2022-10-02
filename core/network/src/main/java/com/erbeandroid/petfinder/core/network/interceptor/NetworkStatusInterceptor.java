package com.erbeandroid.petfinder.core.network.interceptor;

import androidx.annotation.NonNull;

import com.erbeandroid.petfinder.core.network.util.ConnectionManager;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkStatusInterceptor implements Interceptor {

    private final ConnectionManager connectionManager;

    @Inject
    public NetworkStatusInterceptor(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (connectionManager.isConnected()) {
            return chain.proceed(chain.request());
        } else {
            throw new IOException("No network available");
        }
    }
}