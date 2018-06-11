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
    private List<Feature> mFeatureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "In onCreate()");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        quakerPresenter = new QuakerPresenter(this);
        quakerPresenter.fetchOneAndAboveEarthquakeData();
        layoutManager = new LinearLayoutManager(this,
                            LinearLayoutManager.VERTICAL, false);
        earthquakeRV.setLayoutManager(layoutManager);
        earthquakeAdapter = new EarthquakeAdapter();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "In onRestoreInstanceState()");
    }

    @Override
    public void quakesData(List<Feature> featureList) {
        Log.i(TAG, "In quakesData(), earthquake data size is: " + featureList.size());
        mFeatureList = featureList;
        earthquakeAdapter.setData(featureList);
        earthquakeRV.setAdapter(earthquakeAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In onPause()");
        //read current position
//        index = layoutManager.findFirstVisibleItemPosition();
//        View v = earthquakeRV.getChildAt(0);
//        top = (v == null) ? 0 : (v.getTop() - earthquakeRV.getPaddingTop());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In onResume(), mFeatureList size is: " + mFeatureList);
        //set the position
        if(index != -1) {
            layoutManager.scrollToPositionWithOffset(index, top);
        }

        /*TODO:
          Every time the activity resumes, the item will be hidden
          even though the user didn't initiate
         */
//        if (Utils.getHideItemPositionList().size() > 0) {
//            earthquakeAdapter.hideItem(Utils.getPositionForHide());
//        }

        showAllItems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    Log.i(TAG, "Showing all items");
                    Utils.resetHideItemList();
                    /*TODO: leverage cached data and don't make another API call
                        TODO: notify/update adapter?
                     */
//                    quakerPresenter.fetchSignificantEarthquakeData();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In onStop()");
        //read current position
        index = layoutManager.findFirstVisibleItemPosition();
        View v = earthquakeRV.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - earthquakeRV.getPaddingTop());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In onDestroy()");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
