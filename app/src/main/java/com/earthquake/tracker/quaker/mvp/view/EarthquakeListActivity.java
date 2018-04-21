package com.earthquake.tracker.quaker.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.earthquake.tracker.quaker.R;
import com.earthquake.tracker.quaker.mvp.helper.Utils;
import com.earthquake.tracker.quaker.mvp.model.Feature;
import com.earthquake.tracker.quaker.mvp.presenter.QuakerPresenter;
import com.earthquake.tracker.quaker.mvp.presenter.adapter.EarthquakeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarthquakeListActivity extends AppCompatActivity implements QuakerView {

    @BindView(R.id.showAll)
    Switch showAllItems;

    @BindView(R.id.quakerRV)
    RecyclerView earthquakeRV;

    public static final String TAG = EarthquakeListActivity.class.getSimpleName();
    private QuakerPresenter quakerPresenter;
    private EarthquakeAdapter earthquakeAdapter;
    private LinearLayoutManager layoutManager;
    private static int index = -1;
    private static int top = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        quakerPresenter = new QuakerPresenter(this);
        quakerPresenter.fetchSignificantEarthquakeData();
        layoutManager = new LinearLayoutManager(this,
                            LinearLayoutManager.VERTICAL, false);
        earthquakeRV.setLayoutManager(layoutManager);
        earthquakeAdapter = new EarthquakeAdapter();
    }

    @Override
    public void quakesData(List<Feature> featureList) {
        Log.i(TAG, "Earthquake data (BEFORE hiding) size is: " + featureList.size());
        earthquakeAdapter.setData(featureList);
        earthquakeRV.setAdapter(earthquakeAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In onPause()");
        //read current position
        index = layoutManager.findFirstVisibleItemPosition();
        View v = earthquakeRV.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - earthquakeRV.getPaddingTop());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In onResume()");
        //set the position
        if(index != -1) {
            layoutManager.scrollToPositionWithOffset(index, top);
        }

        showAllItems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    Utils.resetHideItemList();
                    quakerPresenter.fetchSignificantEarthquakeData();
                }
            }
        });

        if (Utils.getHideItemPositionList().size() > 0) {
            earthquakeAdapter.hideItem(Utils.getPositionForHide());
        }
    }

}
