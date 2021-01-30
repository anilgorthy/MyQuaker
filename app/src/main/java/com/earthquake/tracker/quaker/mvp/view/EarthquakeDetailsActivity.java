package com.earthquake.tracker.quaker.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.earthquake.tracker.quaker.R;
import com.earthquake.tracker.quaker.databinding.ActivityDetailsBinding;
import com.earthquake.tracker.quaker.mvp.helper.Utils;

public class EarthquakeDetailsActivity extends AppCompatActivity {

    private static final String FEATURE_DATE = "feature_date";
    private static final String FEATURE_MAGNITUDE = "feature_magnitude";
    private static final String FEATURE_ADDRESS = "feature_address";
    private static final String FEATURE_URL = "feature_url";
    private static final String FEATURE_POSITION = "feature_position";
    private static final String TAG = EarthquakeDetailsActivity.class.getSimpleName();
    private int position;
    private ActivityDetailsBinding binding;

    public static Intent createIntent(final Context context, final String date,
                                      final String mag, final String address,
                                      final String url, final int position) {
        final Intent detailsIntent = new Intent(context, EarthquakeDetailsActivity.class);
        detailsIntent.putExtra(FEATURE_DATE, date);
        detailsIntent.putExtra(FEATURE_MAGNITUDE, mag);
        detailsIntent.putExtra(FEATURE_ADDRESS, address);
        detailsIntent.putExtra(FEATURE_URL, url);
        detailsIntent.putExtra(FEATURE_POSITION, position);
        return detailsIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "In onCreate()");
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Intent intent = getIntent();

        if (null != intent) {
            binding.dateValueTV.setText(intent.getStringExtra(FEATURE_DATE));
            binding.magnitudeValueTV.setText(intent.getStringExtra(FEATURE_MAGNITUDE));
            binding.locationValueTV.setText(intent.getStringExtra(FEATURE_ADDRESS));
            binding.urlValueTV.setText(intent.getStringExtra(FEATURE_URL));
            position = intent.getIntExtra(FEATURE_POSITION, -1);
        }

        binding.hideItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    /*TODO:
                        Handle for when the user goes "checked/unchecked"; perhaps
                        preserve the last known state before the user exited this screen
                     */
                    Utils.setPositionToHide(position);
                    Log.i(TAG, "Index of item selected for hide is: " + position);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In onResume()");
    }

}
