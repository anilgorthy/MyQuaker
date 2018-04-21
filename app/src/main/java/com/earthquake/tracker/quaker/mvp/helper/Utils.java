package com.earthquake.tracker.quaker.mvp.helper;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static final String DATE_TIME_FORMAT = "MMMM dd, yyyy HH:mm:ss";

    public static String getFormattedDate(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        Log.i(TAG, "SimpleDateFormat Date is: " + simpleDateFormat.format(calendar.getTime()));

        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getAddress(Context context, List<Double> coordinates) {
        Double latitude = 0.0;
        Double longitude = 0.0;
        for (int i = 0; i < coordinates.size(); i++) {
            longitude = coordinates.get(0);
            latitude = coordinates.get(1);
            Log.i(TAG, "longitude is: " + longitude
                    + " and latitude is: " + latitude);
        }

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Log.e(TAG, "Exception getting address: " + e.getMessage());
        }

        String city = "";
        String state = "";
        String country = "";
        if (null != addresses && addresses.size() > 0) {
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
//            String zip = addresses.get(0).getPostalCode();
            country = addresses.get(0).getCountryName();
        }

        return city + ", " + state + ", " + country;
    }

}
