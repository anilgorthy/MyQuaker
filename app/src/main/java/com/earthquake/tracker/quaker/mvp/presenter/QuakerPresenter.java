package com.earthquake.tracker.quaker.mvp.presenter;

import android.util.Log;

import com.earthquake.tracker.quaker.mvp.model.ApiResponse;
import com.earthquake.tracker.quaker.mvp.model.Earthquake;
import com.earthquake.tracker.quaker.mvp.model.network.ApiCallback;
import com.earthquake.tracker.quaker.mvp.model.network.UsgsRestClient;
import com.earthquake.tracker.quaker.mvp.view.QuakerView;

public class QuakerPresenter {

    private QuakerView quakerView;
    private UsgsRestClient usgsRestClient;
    private static final String TAG = QuakerPresenter.class.getSimpleName();

    public QuakerPresenter(QuakerView quakerView) {
        this.quakerView = quakerView;
        if (null == this.usgsRestClient) {
            this.usgsRestClient = new UsgsRestClient();
        }
    }

    public void fetchSignificantEarthquakeData() {
        usgsRestClient.getSignificantEarthquakes(new ApiCallback<Earthquake>() {
            @Override
            public void onResponse(ApiResponse<Earthquake> apiResponse) {
                final Earthquake earthquake = apiResponse.getResponseObject();
                quakerView.quakesData(earthquake.getFeatures());
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Error fetching data from USGS: " + message);
            }
        });
    }

    public void fetchOneAndAboveEarthquakeData() {
        usgsRestClient.getMagOneAboveEarthquakes(new ApiCallback<Earthquake>() {
            @Override
            public void onResponse(ApiResponse<Earthquake> apiResponse) {
                final Earthquake earthquake = apiResponse.getResponseObject();
                quakerView.quakesData(earthquake.getFeatures());
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Error fetching data from USGS: " + message);
            }
        });
    }

    /**
     * FYI: The data size is ~10k so, for this demo not using this API
     */
    public void fetchAllEarthquakeData() {
        usgsRestClient.getAllEarthquakes(new ApiCallback<Earthquake>() {
            @Override
            public void onResponse(ApiResponse<Earthquake> apiResponse) {
                final Earthquake earthquake = apiResponse.getResponseObject();
                quakerView.quakesData(earthquake.getFeatures());
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Error fetching data from USGS: " + message);
            }
        });
    }

}
