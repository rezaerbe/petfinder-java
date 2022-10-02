package com.erbeandroid.petfinder.core.network.interceptor;

import static com.erbeandroid.petfinder.core.network.util.NetworkConstant.AUTH_ENDPOINT;
import static com.erbeandroid.petfinder.core.network.util.NetworkConstant.CLIENT_ID;
import static com.erbeandroid.petfinder.core.network.util.NetworkConstant.CLIENT_SECRET;

import androidx.annotation.NonNull;

import com.erbeandroid.petfinder.core.network.model.TokenResponse;
import com.erbeandroid.petfinder.core.preference.datasource.PreferenceDataSource;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

import javax.inject.Inject;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AuthenticationInterceptor implements Interceptor {

    private static final Integer UNAUTHORIZED = 401;
    private final PreferenceDataSource preferenceDataSource;
    private final Moshi moshi;

    @Inject
    public AuthenticationInterceptor(PreferenceDataSource preferenceDataSource, Moshi moshi) {
        this.preferenceDataSource = preferenceDataSource;
        this.moshi = moshi;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) {
        String accessToken = preferenceDataSource.getAccessToken();
        Instant expiresIn = Instant.ofEpochSecond(preferenceDataSource.getExpiresIn());

        Request request = chain.request();
        Request interceptedRequest;

        if (expiresIn.isAfter(Instant.now())) {
            // API Request
            interceptedRequest = createAuthenticatedRequest(chain, accessToken);
        } else {
            // API Authentication
            Response tokenResponse = refreshToken(chain);

            // API Request
            interceptedRequest = tokenRequest(chain, tokenResponse, request);
        }

        return proceedDeletingTokenIfUnauthorized(chain, interceptedRequest);
    }

    private Request createAuthenticatedRequest(Interceptor.Chain chain, String accessToken) {
        return chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer " + accessToken)
            .build();
    }

    private Response refreshToken(Interceptor.Chain chain) {
        FormBody body = new FormBody.Builder()
            .add("grant_type", "client_credentials")
            .add("client_id", CLIENT_ID)
            .add("client_secret", CLIENT_SECRET)
            .build();

        HttpUrl url = Objects.requireNonNull(
            chain.request()
                .url()
                .newBuilder(AUTH_ENDPOINT)
        ).build();

        Request tokenRefresh = chain.request()
            .newBuilder()
            .post(body)
            .url(url)
            .build();

        return proceedDeletingTokenIfUnauthorized(chain, tokenRefresh);
    }

    private Request tokenRequest(Interceptor.Chain chain, Response tokenResponse, Request request) {
        if (tokenResponse.isSuccessful()) {
            TokenResponse newToken = mapToken(tokenResponse);
            if (newToken.isValid()) {
                storeToken(newToken);
                return createAuthenticatedRequest(chain, newToken.getAccessToken());
            } else {
                return request;
            }
        } else {
            return request;
        }
    }

    private Response proceedDeletingTokenIfUnauthorized(Interceptor.Chain chain, Request request) {
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Objects.requireNonNull(response).code() == UNAUTHORIZED) {
            preferenceDataSource.deleteToken();
        }

        return response;
    }

    private TokenResponse mapToken(Response tokenResponse) {
        JsonAdapter<TokenResponse> tokenAdapter = moshi.adapter(TokenResponse.class);
        ResponseBody responseBody = tokenResponse.body();

        TokenResponse newTokenResponse = null;
        try {
            newTokenResponse = tokenAdapter.fromJson(Objects.requireNonNull(responseBody).string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newTokenResponse != null ? newTokenResponse : TokenResponse.INVALID;
    }

    private void storeToken(TokenResponse tokenResponse) {
        preferenceDataSource.putTokenType(tokenResponse.getTokenType());
        preferenceDataSource.putExpiresIn(tokenResponse.getExpiresIn());
        preferenceDataSource.putAccessToken(tokenResponse.getAccessToken());
    }
}