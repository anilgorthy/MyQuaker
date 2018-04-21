package com.earthquake.tracker.quaker.mvp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.earthquake.tracker.quaker.R;
import com.earthquake.tracker.quaker.mvp.helper.Utils;
import com.earthquake.tracker.quaker.mvp.model.Feature;
import com.earthquake.tracker.quaker.mvp.view.EarthquakeDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    private static final String TAG = EarthquakeAdapter.class.getSimpleName();
    private List<Feature> featureList;

    public EarthquakeAdapter(List<Feature> featureList) {
        this.featureList = featureList;
    }

    @Override
    public int getItemCount() {
        return featureList.size();
    }

    @Override
    public void onBindViewHolder(EarthquakeViewHolder holder, int position) {
        holder.bindData(featureList.get(position));
    }

    @Override
    public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quaker_item, parent, false);
        return new EarthquakeViewHolder(rowView);
    }

    public static class EarthquakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.dateTV)
        TextView quakeDate;

        @BindView(R.id.magnitudeValueTV)
        TextView magnitudeValue;

        final Context context;
        private Feature feature;

        EarthquakeViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bindData(Feature feature) {
            this.feature = feature;
            Log.i(TAG, "Value is: " + feature.getProperties().getMag()
                            + " and date is: " + Utils.getFormattedDate(feature.getProperties().getTime()));
            quakeDate.setText(Utils.getFormattedDate(feature.getProperties().getTime()));
            magnitudeValue.setText((Double.toString(feature.getProperties().getMag())));
        }

        @Override
        public void onClick(View view) {
            Log.i(TAG, "Feature Id selected at pos: " + getAdapterPosition()
                            + " is: " + feature.getId());
            final Intent detailsIntent = EarthquakeDetailsActivity
                                            .createIntent(view.getContext(),
                                                Utils.getFormattedDate(feature.getProperties().getTime()),
                                                (Double.toString(feature.getProperties().getMag())),
                                                Utils.getAddress(view.getContext(),
                                                        feature.getGeometry().getCoordinates()));
            view.getContext().startActivity(detailsIntent);
        }
    }

}
