package com.erbeandroid.petfinder.core.network.util;

import android.content.Context;
import android.net.ConnectivityManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class ConnectionManager {

    private final Context context;

    @Inject
    public ConnectionManager(@ApplicationContext Context context) {
        this.context = context;
    }

    public Boolean isConnected() {
        ConnectivityManager connectivityManager =
            context.getSystemService(ConnectivityManager.class);
        return connectivityManager.getActiveNetwork() != null;
    }
}