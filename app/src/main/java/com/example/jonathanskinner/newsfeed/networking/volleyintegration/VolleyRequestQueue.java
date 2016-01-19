package com.example.jonathanskinner.newsfeed.networking.volleyintegration;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by jonathanskinner on 1/18/16.
 */
public class VolleyRequestQueue {
    private static VolleyRequestQueue mQueueInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private VolleyRequestQueue(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyRequestQueue getInstance(Context context) {
        if (mQueueInstance == null) {
            mQueueInstance = new VolleyRequestQueue(context);
        }
        return mQueueInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
