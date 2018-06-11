package com.earthquake.tracker.quaker.mvp;

import android.app.Application;

import com.earthquake.tracker.quaker.mvp.model.network.UsgsRestClient;

public class QuakerApplication extends Application {

    private static QuakerApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        UsgsRestClient.init(this);
    }

    public static QuakerApplication getQuakerApplication() {
        return application;
    }

}
