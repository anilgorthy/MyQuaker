package com.earthquake.tracker.quaker.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    private static final String FEATURE_DATE = "feature_id";
    private static final String FEATURE_MAGNITUDE = "feature_magnitude";
    private static final String FEATURE_ADDRESS = "feature_address";

    public static Intent createIntent(final Context context, final String date, final String mag, final String address) {
        final Intent detailsIntent = new Intent(context, EarthquakeDetailsActivity.class);
        detailsIntent.putExtra(FEATURE_DATE, date);
        detailsIntent.putExtra(FEATURE_MAGNITUDE, mag);
        detailsIntent.putExtra(FEATURE_ADDRESS, address);
        return detailsIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setTitle("Earthquake Detail");
        final Intent intent = getIntent();

        if (null != intent) {
            dateValueTV.setText(intent.getStringExtra(FEATURE_DATE));
            magnitudeValueTV.setText(intent.getStringExtra(FEATURE_MAGNITUDE));
            locationValueTV.setText(intent.getStringExtra(FEATURE_ADDRESS));
        }
    }

}
