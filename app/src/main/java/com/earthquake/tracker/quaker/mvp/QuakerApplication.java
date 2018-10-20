package com.earthquake.tracker.quaker.mvp;

import android.app.Application;

import com.earthquake.tracker.quaker.mvp.model.network.UsgsRestClient;
import com.squareup.leakcanary.LeakCanary;

public class QuakerApplication extends Application {

    private static QuakerApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        UsgsRestClient.init(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static QuakerApplication getQuakerApplication() {
        return application;
    }

}
