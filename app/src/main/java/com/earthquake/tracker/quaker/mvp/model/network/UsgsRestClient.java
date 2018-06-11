package com.earthquake.tracker.quaker.mvp.model.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.earthquake.tracker.quaker.mvp.model.ApiResponse;
import com.earthquake.tracker.quaker.mvp.model.Earthquake;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class UsgsRestClient {

    private static final String TAG = UsgsRestClient.class.getSimpleName();
    private static final String BASE_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";
    private static UsgsApiEndpointInterface usgsApiEndpointInterface;
    private static Retrofit usgsRetrofit;

    public static void init(Context context) {
        initRetrofit(context);
    }

    private static void initRetrofit(Context context) {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        // Create the logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                            .cache(cache)
//                                            .addNetworkInterceptor(networkCacheInterceptor)
                                            .addInterceptor(loggingInterceptor)
                                            .build();

        usgsRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public void getMagOneAboveEarthquakes(final ApiCallback<Earthquake> callback) {
        usgsApiEndpointInterface = usgsRetrofit.create(UsgsApiEndpointInterface.class);

        final Call<Earthquake> call = usgsApiEndpointInterface.getMagnitudeOneAndAboveEarthquakesFor30Days();
        call.enqueue(new Callback<Earthquake>() {
            @Override
            public void onResponse(@NonNull final Call<Earthquake> call, @NonNull final Response<Earthquake> response) {
                if (response.isSuccessful() && response.raw().cacheResponse() != null) {
                    Log.d(TAG, "response came from cache");
                    callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                }

                if(response.isSuccessful() && response.raw().networkResponse() != null) {
                    Log.d(TAG, "response came from server");
                    callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull final Call<Earthquake> call, @NonNull final Throwable t) {
                Log.e(TAG, "Failure when fetching response: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void get30DayEarthquakesSignificant(final ApiCallback<Earthquake> callback) {
        usgsApiEndpointInterface = usgsRetrofit.create(UsgsApiEndpointInterface.class);

        final Call<Earthquake> call = usgsApiEndpointInterface.getSignificantEarthquakesFor30Days();
        call.enqueue(new Callback<Earthquake>() {
            @Override
            public void onResponse(@NonNull final Call<Earthquake> call, @NonNull final Response<Earthquake> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull final Call<Earthquake> call, @NonNull final Throwable t) {
                Log.e(TAG, "Failure when fetching response: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void get30DayEarthquakesAll(final ApiCallback<Earthquake> callback) {
        usgsApiEndpointInterface = usgsRetrofit.create(UsgsApiEndpointInterface.class);

        final Call<Earthquake> call = usgsApiEndpointInterface.getAllEarthquakesFor30Days();
        call.enqueue(new Callback<Earthquake>() {
            @Override
            public void onResponse(@NonNull final Call<Earthquake> call, @NonNull final Response<Earthquake> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull final Call<Earthquake> call, @NonNull final Throwable t) {
                Log.e(TAG, "Failure when fetching response: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface UsgsApiEndpointInterface {

        @GET("1.0_month.geojson")
        Call<Earthquake> getMagnitudeOneAndAboveEarthquakesFor30Days();

        @GET("significant_month.geojson")
        Call<Earthquake> getSignificantEarthquakesFor30Days();

        @GET("all_month.geojson")
        Call<Earthquake> getAllEarthquakesFor30Days();
    }

}
