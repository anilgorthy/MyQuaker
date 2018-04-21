package com.earthquake.tracker.quaker.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.earthquake.tracker.quaker.R;
import com.earthquake.tracker.quaker.mvp.model.Earthquake;
import com.earthquake.tracker.quaker.mvp.presenter.QuakerPresenter;
import com.earthquake.tracker.quaker.mvp.view.adapter.EarthquakeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements QuakerView {

    @BindView(R.id.quakerRV)
    RecyclerView earthquakeRV;

    public static final String TAG = MainActivity.class.getSimpleName();
    private QuakerPresenter quakerPresenter;
    private EarthquakeAdapter earthquakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        quakerPresenter = new QuakerPresenter(this);
        quakerPresenter.fetchSignificantEarthquakeData();
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false);
        earthquakeRV.setLayoutManager(layoutManager);
    }

    @Override
    public void quakesData(Earthquake earthquake) {
        Log.i(TAG, "Earthquake type is: " + earthquake.getType()
                    + " and data size is: " + earthquake.getFeatures().size());
        earthquakeAdapter = new EarthquakeAdapter(earthquake.getFeatures());
        earthquakeRV.setAdapter(earthquakeAdapter);
    }

}
