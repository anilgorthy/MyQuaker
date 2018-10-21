package com.earthquake.tracker.quaker.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.earthquake.tracker.quaker.R;
import com.earthquake.tracker.quaker.mvp.model.Feature;
import com.earthquake.tracker.quaker.mvp.presenter.QuakerPresenter;
import com.earthquake.tracker.quaker.mvp.presenter.adapter.EarthquakeAdapter;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarthquakeListActivity extends AppCompatActivity
        implements QuakerView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.showHide)
    Switch showHide;

    @BindView(R.id.quakerRV)
    RecyclerView earthquakeRV;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static final String TAG = EarthquakeListActivity.class.getSimpleName();
    private QuakerPresenter quakerPresenter;
    private EarthquakeAdapter earthquakeAdapter;
    private LinearLayoutManager layoutManager;
    private static int index = -1;
    private static int top = -1;
    private List<Feature> featureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "In onCreate()");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showHide.setOnCheckedChangeListener(this);
        quakerPresenter = new QuakerPresenter(this);
        quakerPresenter.fetchOneAndAboveEarthquakeData();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        earthquakeRV.setLayoutManager(layoutManager);
        earthquakeAdapter = new EarthquakeAdapter();

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                progressBar.setVisibility(View.GONE);
                quakerPresenter.displayMarkers(mapboxMap);
            }
        });
    }

    @Override
    public void quakesData(List<Feature> featureList) {
        this.featureList = featureList;
        Log.i(TAG, "In quakesData(), earthquake data size is: " + this.featureList.size());
        progressBar.setVisibility(View.GONE);
        earthquakeAdapter.setData(this.featureList);
        earthquakeRV.setAdapter(earthquakeAdapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            Log.i(TAG, "Showing Map");
            mapView.setVisibility(View.VISIBLE);
            earthquakeRV.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "Showing List");
            earthquakeRV.setVisibility(View.VISIBLE);
            mapView.setVisibility(View.GONE);
        }
    }

    //region lifecycle calls
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //set the position
        if (index != -1) {
            layoutManager.scrollToPositionWithOffset(index, top);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        Log.i(TAG, "In onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        Log.i(TAG, "In onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        Log.i(TAG, "In onPause()");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        Log.i(TAG, "In onLowMemory()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        Log.i(TAG, "In onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "In onRestoreInstanceState()");
    }
    //end region
}
