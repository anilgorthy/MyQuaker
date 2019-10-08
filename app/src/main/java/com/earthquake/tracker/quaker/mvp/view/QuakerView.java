package com.earthquake.tracker.quaker.mvp.view;

import com.earthquake.tracker.quaker.mvp.model.Feature;

import java.util.List;

public interface QuakerView {
    void quakesData(List<Feature> featuresList);
    void handleEarthquakeType();
}
