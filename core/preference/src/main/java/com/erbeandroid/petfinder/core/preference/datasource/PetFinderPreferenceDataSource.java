package com.erbeandroid.petfinder.core.preference.datasource;

import static com.erbeandroid.petfinder.core.preference.util.PreferenceConstant.KEY_ACCESS_TOKEN;
import static com.erbeandroid.petfinder.core.preference.util.PreferenceConstant.KEY_EXPIRES_IN;
import static com.erbeandroid.petfinder.core.preference.util.PreferenceConstant.KEY_TOKEN_TYPE;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class PetFinderPreferenceDataSource implements PreferenceDataSource {

    private final SharedPreferences preferences;

    @Inject
    public PetFinderPreferenceDataSource(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    SharedPreferences.Editor edit() {
        return preferences.edit();
    }

    @Override
    public void putTokenType(String tokenType) {
        edit().putString(KEY_TOKEN_TYPE, tokenType).commit();
    }

    @Override
    public void putExpiresIn(Long expiresIn) {
        edit().putLong(KEY_EXPIRES_IN, expiresIn).commit();
    }

    @Override
    public void putAccessToken(String accessToken) {
        edit().putString(KEY_ACCESS_TOKEN, accessToken).commit();
    }

    @Override
    public Long getExpiresIn() {
        return preferences.getLong(KEY_EXPIRES_IN, -1);
    }

    @Override
    public String getAccessToken() {
        return preferences.getString(KEY_ACCESS_TOKEN, "");
    }

    @Override
    public void deleteToken() {
        edit()
            .remove(KEY_TOKEN_TYPE)
            .remove(KEY_EXPIRES_IN)
            .remove(KEY_ACCESS_TOKEN)
            .commit();
    }
}