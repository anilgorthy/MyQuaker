package com.earthquake.tracker.quaker.mvp.model.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.earthquake.tracker.quaker.mvp.helper.Utils;
import com.earthquake.tracker.quaker.mvp.model.ApiResponse;
import com.earthquake.tracker.quaker.mvp.model.Earthquake;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
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

//    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
//        @Override
//        public okhttp3.Response intercept(Chain chain) throws IOException {
//            okhttp3.Response originalResponse = chain.proceed(chain.request());
//            if (Utils.isNetworkAvailable()) {
//                int maxAge = 60; // read from cache for 1 minute
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//        }
//    };

    private static void initRetrofit(Context context) {

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        // Create the logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                            .cache(cache)
                                            .addNetworkInterceptor(new Interceptor() {
                                                @Override
                                                public okhttp3.Response intercept(Chain chain) throws IOException {
                                                    okhttp3.Response originalResponse = chain.proceed(chain.request());
                                                    if (Utils.isNetworkAvailable()) {
                                                        Log.i(TAG, "Connected to the data network");
                                                        int maxAge = 600; // read from cache for 10 minutes
                                                        return originalResponse.newBuilder()
                                                                .header("Cache-Control", "public, max-age=" + maxAge)
                                                                .build();
                                                    } else {
                                                        Log.i(TAG, "NOT connected to the data network");
                                                        int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                                                        return originalResponse.newBuilder()
                                                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                                                .build();
                                                    }
                                                }
                                            })
                                            .addInterceptor(loggingInterceptor)
                                            .build();

        usgsRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        usgsApiEndpointInterface = usgsRetrofit.create(UsgsApiEndpointInterface.class);
    }


    public void getMagOneAboveEarthquakes(final ApiCallback<Earthquake> callback) {
//        usgsApiEndpointInterface = usgsRetrofit.create(UsgsApiEndpointInterface.class);

        final Call<Earthquake> call = usgsApiEndpointInterface.getMagnitudeOneAndAboveEarthquakesFor30Days();
        call.enqueue(new Callback<Earthquake>() {
            @Override
            public void onResponse(@NonNull final Call<Earthquake> call, @NonNull final Response<Earthquake> response) {
                if (response.isSuccessful() && response.raw().cacheResponse() != null && response.body() != null) {
                    Log.d(TAG, "Response from cache");
                    callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                }

                if(response.isSuccessful() && response.raw().networkResponse() != null && response.body() != null) {
                    Log.d(TAG, "Response from server w/ data size: "
                                                + response.body().getFeatures().size());
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

    public void getSignificantEarthquakes(final ApiCallback<Earthquake> callback) {
//        usgsApiEndpointInterface = usgsRetrofit.create(UsgsApiEndpointInterface.class);

        final Call<Earthquake> call = usgsApiEndpointInterface.getSignificantEarthquakesFor30Days();
        call.enqueue(new Callback<Earthquake>() {
            @Override
            public void onResponse(@NonNull final Call<Earthquake> call, @NonNull final Response<Earthquake> response) {
                if (response.isSuccessful() && response.raw().cacheResponse() != null) {
                    Log.d(TAG, "Response from cache");
                    callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                }

                if(response.isSuccessful() && response.raw().networkResponse() != null) {
                    Log.d(TAG, "Response from server w/ status code: "
                            + response.code());
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

    public void getAllEarthquakes(final ApiCallback<Earthquake> callback) {
//        usgsApiEndpointInterface = usgsRetrofit.create(UsgsApiEndpointInterface.class);

        final Call<Earthquake> call = usgsApiEndpointInterface.getAllEarthquakesFor30Days();
        call.enqueue(new Callback<Earthquake>() {
            @Override
            public void onResponse(@NonNull final Call<Earthquake> call, @NonNull final Response<Earthquake> response) {
                if (response.isSuccessful() && response.raw().cacheResponse() != null) {
                    Log.d(TAG, "Response from cache");
                    callback.onResponse(new ApiResponse<>(response.code(), response.body()));
                }

                if(response.isSuccessful() && response.raw().networkResponse() != null) {
                    Log.d(TAG, "Response from server w/ status code: "
                            + response.code());
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
