package com.earthquake.tracker.quaker.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feature {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("properties")
    @Expose
    private Properties properties;

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("id")
    @Expose
    private String id;

    private boolean isHiddenState;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHideState(final boolean state) {
        this.isHiddenState = state;
    }

    public boolean isHiddenState() {
        return isHiddenState;
    }

}
