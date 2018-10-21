package com.earthquake.tracker.quaker.mvp.presenter;

import android.util.Log;

import com.earthquake.tracker.quaker.mvp.model.ApiResponse;
import com.earthquake.tracker.quaker.mvp.model.Earthquake;
import com.earthquake.tracker.quaker.mvp.model.Feature;
import com.earthquake.tracker.quaker.mvp.model.network.ApiCallback;
import com.earthquake.tracker.quaker.mvp.model.network.UsgsRestClient;
import com.earthquake.tracker.quaker.mvp.view.QuakerView;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

public class QuakerPresenter {

    private QuakerView quakerView;
    private UsgsRestClient usgsRestClient;
    private Earthquake earthquake;
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
                earthquake = apiResponse.getResponseObject();
                quakerView.quakesData(earthquake.getFeatures());
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Error fetching data from USGS: " + message);
            }
        });
    }

    /**
     * FYI: The data size is ~8k
     */
    public void fetchOneAndAboveEarthquakeData() {
        usgsRestClient.getMagOneAboveEarthquakes(new ApiCallback<Earthquake>() {
            @Override
            public void onResponse(ApiResponse<Earthquake> apiResponse) {
                earthquake = apiResponse.getResponseObject();
                quakerView.quakesData(earthquake.getFeatures());
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Error fetching data from USGS: " + message);
            }
        });
    }

    /**
     * FYI: The data size is over 10k
     */
    public void fetchAllEarthquakeData() {
        usgsRestClient.getAllEarthquakes(new ApiCallback<Earthquake>() {
            @Override
            public void onResponse(ApiResponse<Earthquake> apiResponse) {
                earthquake = apiResponse.getResponseObject();
                quakerView.quakesData(earthquake.getFeatures());
            }

            @Override
            public void onFailure(String message) {
                Log.e(TAG, "Error fetching data from USGS: " + message);
            }
        });
    }

    public void displayMarkers(MapboxMap mapboxMap) {
        List<MarkerOptions> markers = new ArrayList<>();
        Log.i(TAG, "Current zoom level is: " + mapboxMap.getCameraPosition().zoom
                + " and earthquake data size is: " + earthquake.getFeatures().size());
        for (Feature feature : earthquake.getFeatures()) {
            List<Double> coordinatesList = feature.getGeometry().getCoordinates();
            for (int i = 0; i < coordinatesList.size(); i++) {
                markers.add(new MarkerOptions()
                        .position(new LatLng(coordinatesList.get(1), coordinatesList.get(0)))
                        .title(Double.toString(feature.getProperties().getMag())));
            }
        }
        mapboxMap.addMarkers(markers);
    }

}
