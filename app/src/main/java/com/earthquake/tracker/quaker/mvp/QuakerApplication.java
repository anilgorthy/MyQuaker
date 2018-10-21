package com.earthquake.tracker.quaker.mvp;

import android.app.Application;

import com.earthquake.tracker.quaker.R;
import com.earthquake.tracker.quaker.mvp.model.network.UsgsRestClient;
import com.mapbox.mapboxsdk.Mapbox;

public class QuakerApplication extends Application {

    private static QuakerApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        UsgsRestClient.init(this);
        // Mapbox Access token
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
    }

    public static QuakerApplication getQuakerApplication() {
        return application;
    }

}
