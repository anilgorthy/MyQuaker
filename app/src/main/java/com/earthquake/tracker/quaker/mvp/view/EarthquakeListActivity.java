package com.earthquake.tracker.quaker.mvp.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.earthquake.tracker.quaker.R;
import com.earthquake.tracker.quaker.databinding.ActivityMainBinding;
import com.earthquake.tracker.quaker.mvp.helper.Utils;
import com.earthquake.tracker.quaker.mvp.model.Feature;
import com.earthquake.tracker.quaker.mvp.presenter.QuakerPresenter;
import com.earthquake.tracker.quaker.mvp.presenter.adapter.EarthquakeAdapter;

import java.util.List;

public class EarthquakeListActivity extends AppCompatActivity
        implements QuakerView, CompoundButton.OnCheckedChangeListener {

    private ActivityMainBinding binding;

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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.showAll.setOnCheckedChangeListener(this);
        quakerPresenter = new QuakerPresenter(this);
        layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        binding.quakerRV.setLayoutManager(layoutManager);
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
        this.featureList = featureList;
        binding.progressBar.setVisibility(View.GONE);
        earthquakeAdapter.setData(featureList);
        binding.quakerRV.setAdapter(earthquakeAdapter);
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
        Log.i(TAG, "In onResume()");
        //set the position
        if (index != -1) {
            layoutManager.scrollToPositionWithOffset(index, top);
        }

        /*TODO:
          Every time the activity resumes, the item will be hidden
          even though the user didn't initiate
         */
        if (Utils.getHideItemPositionList().size() > 0) {
            earthquakeAdapter.hideItem(Utils.getPositionForHide());
        }

        binding.showAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Log.i(TAG, "Showing all items");
                    Utils.resetHideItemList();
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
        View v = binding.quakerRV.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - binding.quakerRV.getPaddingTop());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            Log.i(TAG, "Showing All");
            binding.quakerRV.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "Showing List");
            binding.quakerRV.setVisibility(View.VISIBLE);
        }
    }

    //TODO: need to pass this to presenter so create a method in QuakerView
    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.oneAndAbove:
                binding.progressBar.setVisibility(View.VISIBLE);
                if (checked) {
                    quakerPresenter.fetchOneAndAboveEarthquakeData();
                }
                break;
            case R.id.significant:
                binding.progressBar.setVisibility(View.VISIBLE);
                if (checked) {
                    quakerPresenter.fetchSignificantEarthquakeData();
                }
                break;
            case R.id.all:
                binding.progressBar.setVisibility(View.VISIBLE);
                if (checked) {
                    quakerPresenter.fetchAllEarthquakeData();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void handleEarthquakeType() {
        //TODO
    }
}
