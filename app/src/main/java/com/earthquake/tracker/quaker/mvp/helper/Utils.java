package com.earthquake.tracker.quaker.mvp.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.earthquake.tracker.quaker.mvp.QuakerApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static final String DATE_TIME_FORMAT = "MMMM dd, yyyy HH:mm:ss";

    public static String getFormattedDate(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());

        return simpleDateFormat.format(calendar.getTime());
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) QuakerApplication.getQuakerApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
