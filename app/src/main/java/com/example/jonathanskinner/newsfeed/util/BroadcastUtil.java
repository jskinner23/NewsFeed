package com.example.jonathanskinner.newsfeed.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by jonathanskinner on 1/18/16.
 */
public abstract class BroadcastUtil {

    public static final String ACTION_NO_NETWORK_AVAILABLE = "action_no_network_available";

    private BroadcastUtil() {}

    public static final void sendNoNetworkAvailableBroadcast(Context context) {
        Intent noNetworkAvailableBroadcast = new Intent(ACTION_NO_NETWORK_AVAILABLE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(noNetworkAvailableBroadcast);
    }
}
