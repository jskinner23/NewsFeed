package com.example.jonathanskinner.newsfeed.networking.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.jonathanskinner.newsfeed.model.NewsItem;
import com.example.jonathanskinner.newsfeed.networking.volleyintegration.VolleyRequestQueue;
import com.example.jonathanskinner.newsfeed.util.BroadcastUtil;
import com.example.jonathanskinner.newsfeed.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathanskinner on 1/18/16.
 */
public class NewsFetchService extends IntentService {
    private static final String LOG_TAG = NewsFetchService.class.getName();

    private static final String NEWS_FEED_URL = "http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=8&q=http://news.google.com/news?output=rss";

    public NewsFetchService() {
        super(NewsFetchService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "NewsFetchService handling intent");
        //check for network availability
        if (!NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            BroadcastUtil.sendNoNetworkAvailableBroadcast(getApplicationContext());
            stopSelf();
            return;
        }

        //fetch news
        fetchNews();
    }

    private void fetchNews() {
        Log.d(LOG_TAG, "fetchNews()");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, NEWS_FEED_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(LOG_TAG, "response received: " + response.toString());
                //parse response
                List<NewsItem> newsItems;
                try {
                    newsItems = parseResponse(response);
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "Error retrieving news", error);
            }
        });

        // set retry policy for timing out
        request.setRetryPolicy(new DefaultRetryPolicy(7000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // put request in queue
        VolleyRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private List<NewsItem> parseResponse(JSONObject jsonResponse) throws JSONException {
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        return newsItems;
    }
}
