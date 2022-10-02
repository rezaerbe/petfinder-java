package com.erbeandroid.petfinder.core.network.model;

import com.squareup.moshi.Json;

import java.time.Instant;

public class TokenResponse {
    @Json(name = "token_type")
    private final String tokenType;
    @Json(name = "expires_in")
    private final Integer expiresIn;
    @Json(name = "access_token")
    private final String accessToken;

    public TokenResponse(String tokenType, Integer expiresIn, String accessToken) {
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.accessToken = accessToken;
    }

    public static TokenResponse INVALID = new TokenResponse("", -1, "");

    public Boolean isValid() {
        return tokenType != null && !tokenType.isEmpty() &&
            expiresIn != null && expiresIn >= 0 &&
            accessToken != null && !accessToken.isEmpty();
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getExpiresIn() {
        if (expiresIn == null) return 0L;
        return Instant.now().plusSeconds(Long.valueOf(expiresIn)).getEpochSecond();
    }

    public String getAccessToken() {
        return accessToken;
    }
}