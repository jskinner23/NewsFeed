package com.example.jonathanskinner.newsfeed.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jonathanskinner on 1/18/16.
 */
public final class NetworkUtil {
    private static final String LOG_TAG = NetworkUtil.class.getName();

    private NetworkUtil(){}

    public static boolean isNetworkAvailable(Context context) {
        boolean isConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
