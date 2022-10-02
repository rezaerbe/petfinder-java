package com.erbeandroid.petfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class ConnectionMonitoring extends LiveData<Boolean> {

    private final Context context;

    @Inject
    public ConnectionMonitoring(@ApplicationContext Context context) {
        this.context = context;
    }

    NetworkRequest networkRequest() {
        return new NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build();
    }

    ConnectivityManager.NetworkCallback networkCallback() {
        return new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                postValue(true);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                postValue(false);
            }
        };
    }

    ConnectivityManager connectivityManager() {
        return context.getSystemService(ConnectivityManager.class);
    }

    @Override
    protected void onActive() {
        super.onActive();
        try {
            connectivityManager().registerNetworkCallback(networkRequest(), networkCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        try {
            connectivityManager().unregisterNetworkCallback(networkCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}