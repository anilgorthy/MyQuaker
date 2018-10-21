package com.earthquake.tracker.quaker.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.earthquake.tracker.quaker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarthquakeDetailsActivity extends AppCompatActivity {

    @BindView(R.id.dateValueTV)
    TextView dateValueTV;

    @BindView(R.id.magnitudeValueTV)
    TextView magnitudeValueTV;

    @BindView(R.id.locationValueTV)
    TextView locationValueTV;

    @BindView(R.id.urlValueTV)
    TextView urlValueTV;

    private static final String FEATURE_DATE = "feature_date";
    private static final String FEATURE_MAGNITUDE = "feature_magnitude";
    private static final String FEATURE_ADDRESS = "feature_address";
    private static final String FEATURE_URL = "feature_url";
    private static final String FEATURE_POSITION = "feature_position";
    private static final String TAG = EarthquakeDetailsActivity.class.getSimpleName();
    private int position;

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
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        if (null != intent) {
            dateValueTV.setText(intent.getStringExtra(FEATURE_DATE));
            magnitudeValueTV.setText(intent.getStringExtra(FEATURE_MAGNITUDE));
            locationValueTV.setText(intent.getStringExtra(FEATURE_ADDRESS));
            urlValueTV.setText(intent.getStringExtra(FEATURE_URL));
            position = intent.getIntExtra(FEATURE_POSITION, -1);
        }
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
