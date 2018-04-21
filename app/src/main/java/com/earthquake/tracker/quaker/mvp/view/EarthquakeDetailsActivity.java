package com.earthquake.tracker.quaker.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.earthquake.tracker.quaker.R;
import com.earthquake.tracker.quaker.mvp.helper.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarthquakeDetailsActivity extends AppCompatActivity {

    @BindView(R.id.hideItem)
    Switch showHideSwitch;

    @BindView(R.id.dateValueTV)
    TextView dateValueTV;

    @BindView(R.id.magnitudeValueTV)
    TextView magnitudeValueTV;

    @BindView(R.id.locationValueTV)
    TextView locationValueTV;

    private static final String FEATURE_DATE = "feature_date";
    private static final String FEATURE_MAGNITUDE = "feature_magnitude";
    private static final String FEATURE_ADDRESS = "feature_address";
    private static final String FEATURE_POSITION = "feature_position";
    private static final String TAG = EarthquakeDetailsActivity.class.getSimpleName();
    private int position;

    public static Intent createIntent(final Context context, final String date,
                                      final String mag, final String address,
                                      final int position) {
        final Intent detailsIntent = new Intent(context, EarthquakeDetailsActivity.class);
        detailsIntent.putExtra(FEATURE_DATE, date);
        detailsIntent.putExtra(FEATURE_MAGNITUDE, mag);
        detailsIntent.putExtra(FEATURE_ADDRESS, address);
        detailsIntent.putExtra(FEATURE_POSITION, position);
        return detailsIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        final Intent intent = getIntent();

        if (null != intent) {
            dateValueTV.setText(intent.getStringExtra(FEATURE_DATE));
            magnitudeValueTV.setText(intent.getStringExtra(FEATURE_MAGNITUDE));
            locationValueTV.setText(intent.getStringExtra(FEATURE_ADDRESS));
            position = intent.getIntExtra(FEATURE_POSITION, -1);
        }

        showHideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Utils.setPositionToHide(position);
                    Log.i(TAG, "Index of item selected for hide is: " + position);
                }
            }
        });
    }

}
