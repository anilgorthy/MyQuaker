package com.earthquake.tracker.quaker.mvp;

import android.app.Application;

import com.earthquake.tracker.quaker.mvp.presenter.UsgsRestClient;

public class QuakerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UsgsRestClient.init();
    }
}
