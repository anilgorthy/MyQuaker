package com.earthquake.tracker.quaker.mvp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.earthquake.tracker.quaker.mvp.QuakerApplication;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static final String DATE_TIME_FORMAT = "MMMM dd, yyyy HH:mm:ss";
    private static String city = "";
    private static String state = "";
    private static String country = "";
    private static List<Integer> removedPositions = new ArrayList<>();
    private static final String DEFAULT_SHARED_PREFERENCE = "quaker_tracker_sp";
    private static final String ITEM_POSITION = "item_position";

    public static String getFormattedDate(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());

        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getAddress(Context context, List<Double> coordinates) {
        Double latitude = 0.0;
        Double longitude = 0.0;
        for (int i = 0; i < coordinates.size(); i++) {
            longitude = coordinates.get(0);
            latitude = coordinates.get(1);
        }

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Log.e(TAG, "Exception getting address: " + e.getMessage());
        }

        if (null != addresses && addresses.size() > 0) {
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            //unused
//            String zip = addresses.get(0).getPostalCode();
            country = addresses.get(0).getCountryName();
        }
        //only non-null/empty
        city = city == null ? "" : city + ", ";
        state = state == null ? "" : state + ", ";
        country = country == null ? "" : country;

        return city + state + country;
    }

    public static void resetHideItemList() {
        removedPositions = new ArrayList<>();
    }

    public static List<Integer> getHideItemPositionList() {
        Log.i(TAG, "In getHideItemPositionList() size is: " + removedPositions.size());
        return removedPositions;
    }

    public static void setPositionToHide(final int position) {
        final SharedPreferences sharedPreferences = QuakerApplication.getQuakerApplication()
                .getSharedPreferences(DEFAULT_SHARED_PREFERENCE,
                        Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.i(TAG, "Removed positions size was: " + removedPositions.size());
        editor.putInt(ITEM_POSITION, position).apply();
        removedPositions.add(position);
        Log.i(TAG, "Removed positions size is: " + removedPositions.size());
    }

    public static int getPositionForHide() {
        final SharedPreferences sharedPreferences = QuakerApplication.getQuakerApplication()
                .getSharedPreferences(DEFAULT_SHARED_PREFERENCE,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ITEM_POSITION, -1);
    }

//    public static String getFileCache(Context context) {
//
//    }

}
