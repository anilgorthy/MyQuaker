package com.earthquake.tracker.quaker.mvp.presenter;

import android.util.Log;

import com.earthquake.tracker.quaker.mvp.model.ApiResponse;
import com.earthquake.tracker.quaker.mvp.model.Earthquake;
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
        Log.i(TAG, "Fetching data from USGS");
        usgsRestClient.get30DayEarthquakesSignificant(new ApiCallback<Earthquake>() {
            @Override
            public void onResponse(ApiResponse<Earthquake> apiResponse) {
                Log.i(TAG, "Status code for data fetch from USGS: " + apiResponse.getStatusCode());
                final Earthquake earthquake = apiResponse.getResponseObject();
                quakerView.quakesData(earthquake);
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Status code for data fetch from USGS: " + message);
            }
        });

    }

    /**
     * FYI: The data size is ~1000 so, for this demo not using this API
     */
    public void fetchAllEarthquakeData() {
        Log.i(TAG, "Fetching all 30 days data from USGS");
        usgsRestClient.get30DayEarthquakesAll(new ApiCallback<Earthquake>() {
            @Override
            public void onResponse(ApiResponse<Earthquake> apiResponse) {
                Log.i(TAG, "Status code for data fetch from USGS: " + apiResponse.getStatusCode());
                final Earthquake earthquake = apiResponse.getResponseObject();
                quakerView.quakesData(earthquake);
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Status code for data fetch from USGS: " + message);
            }
        });

    }

}
