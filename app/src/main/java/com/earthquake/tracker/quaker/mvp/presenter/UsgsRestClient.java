package com.earthquake.tracker.quaker.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.earthquake.tracker.quaker.mvp.model.ApiResponse;
import com.earthquake.tracker.quaker.mvp.model.Earthquake;

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
    private static Handler mainHandler;
    private static Retrofit usgsRetrofit;

    public static void init() {
        initRetrofit();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    private static void initRetrofit() {
        usgsRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void get30DayEarthquakesSignificant(final ApiCallback<Earthquake> callback) {
        usgsApiEndpointInterface = usgsRetrofit.create(UsgsApiEndpointInterface.class);

        final Call<Earthquake> call = usgsApiEndpointInterface.getSignificantEarthquakesFor30Days();
        call.enqueue(new Callback<Earthquake>() {
            @Override
            public void onResponse(@NonNull final Call<Earthquake> call, @NonNull final Response<Earthquake> response) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                        }
                    }
                });
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
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull final Call<Earthquake> call, @NonNull final Throwable t) {
                Log.e(TAG, "Failure when fetching response: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface UsgsApiEndpointInterface {

        @GET("significant_month.geojson")
        Call<Earthquake> getSignificantEarthquakesFor30Days();

        @GET("all_month.geojson")
        Call<Earthquake> getAllEarthquakesFor30Days();
    }

}
