package com.earthquake.tracker.quaker.mvp.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.earthquake.tracker.quaker.databinding.QuakerItemBinding;
import com.earthquake.tracker.quaker.mvp.helper.Utils;
import com.earthquake.tracker.quaker.mvp.model.Feature;
import com.earthquake.tracker.quaker.mvp.view.EarthquakeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    private static final String TAG = EarthquakeAdapter.class.getSimpleName();
    private static QuakerItemBinding binding;
    private final List<Feature> featureList;

    public EarthquakeAdapter() {
        featureList = new ArrayList<>();
    }

    public void setData(List<Feature> list) {
        featureList.clear();
        featureList.addAll(list);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "data size is: " + featureList.size());
        return featureList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        Feature featureDisplay = featureList.get(position);
        holder.bindData(featureDisplay);
        holder.itemView.setContentDescription("Earthquake " + position + " : " +
                featureDisplay.getProperties().getTime() + " with " +
                featureDisplay.getProperties().getMag() + " magnitude");
    }

    public void hideItem(final int position) {
        Log.i(TAG, "Before hiding data size is: " + featureList.size());
        if (featureList.size() > 0) {
            featureList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, featureList.size());
            notifyDataSetChanged();
            Log.i(TAG, "After hiding data size is: " + featureList.size());
        }
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = QuakerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EarthquakeViewHolder(binding);
    }

    public static class EarthquakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final Context context;
        private Feature feature;

        EarthquakeViewHolder(@NonNull QuakerItemBinding itemView) {
            super(itemView.getRoot());
            this.context = itemView.getRoot().getContext();
            itemView.getRoot().setOnClickListener(this);
        }

        private void bindData(Feature feature) {
            this.feature = feature;
            binding.dateValTV.setText(Utils.getFormattedDate(feature.getProperties().getTime()));
            binding.magnitudeValueTV.setText((Double.toString(feature.getProperties().getMag())));
        }

        @Override
        public void onClick(View view) {
            final Intent detailsIntent = EarthquakeDetailsActivity
                    .createIntent(view.getContext(),
                            Utils.getFormattedDate(feature.getProperties().getTime()),
                            (Double.toString(feature.getProperties().getMag())),
                            feature.getProperties().getPlace(), feature.getProperties().getUrl(),
                            getAdapterPosition());
            view.getContext().startActivity(detailsIntent);
        }
    }

}
