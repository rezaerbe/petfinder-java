package com.erbeandroid.petfinder.core.preference.datasource;

public interface PreferenceDataSource {

    void putTokenType(String tokenType);

    void putExpiresIn(Long expiresIn);

    void putAccessToken(String accessToken);

    Long getExpiresIn();

    String getAccessToken();

    void deleteToken();
}