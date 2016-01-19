package com.example.jonathanskinner.newsfeed.networking.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.jonathanskinner.newsfeed.model.NewsItem;
import com.example.jonathanskinner.newsfeed.networking.volleyintegration.VolleyRequestQueue;
import com.example.jonathanskinner.newsfeed.service.NewsItemService;
import com.example.jonathanskinner.newsfeed.service.impl.NewsItemServiceImpl;
import com.example.jonathanskinner.newsfeed.util.BroadcastUtil;
import com.example.jonathanskinner.newsfeed.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathanskinner on 1/18/16.
 */
public class NewsFetchService extends IntentService {
    private static final String LOG_TAG = NewsFetchService.class.getName();

    private static final String NEWS_FEED_URL = "http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=*&q=http://news.google.com/news?output=rss";

    private static final Integer MAX_NEWS_ITEMS = 10;

    private static final String NEWS_FEED_JSON_RESPONSE_DATA = "responseData";
    private static final String NEWS_FEED_JSON_FEED = "feed";
    private static final String NEWS_FEED_JSON_ENTRIES = "entries";

    private static final String NEWS_FEED_JSON_ENTRY_TITLE = "title";
    private static final String NEWS_FEED_JSON_ENTRY_LINK = "link";
    private static final String NEWS_FEED_JSON_ENTRY_AUTHOR = "author";
    private static final String NEWS_FEED_JSON_ENTRY_PUBLISHED_DATE = "publishedDate";
    private static final String NEWS_FEED_JSON_ENTRY_CONTENT_SNIPPET = "contentSnippet";
    private static final String NEWS_FEED_JSON_ENTRY_CONTENT = "content";
    private static final String NEWS_FEED_JSON_ENTRY_CATEGORIES = "categories";

    private NewsItemService mNewsItemService = new NewsItemServiceImpl();

    private Context mContext;

    public NewsFetchService() {
        super(NewsFetchService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mContext = getApplicationContext();

        //check for network availability
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            BroadcastUtil.sendNoNetworkAvailableBroadcast(mContext);
            stopSelf();
            return;
        }

        //fetch news
        fetchNews();

        // check if news item count and if there are more than the desired amount, delete the oldest news items
        Integer newsItemCount = mNewsItemService.getCount(mContext);
        Log.d(LOG_TAG, "newsItemCount: " + newsItemCount);
        if (newsItemCount > MAX_NEWS_ITEMS) {
            List<NewsItem> allNewsItems = mNewsItemService.getAll(mContext);
            List<NewsItem> newsItemsToDelete = allNewsItems.subList(10, (allNewsItems.size()-1));
            mNewsItemService.deleteNewsItems(mContext, newsItemsToDelete);
            Log.d(LOG_TAG, "newsItemCount after deletion: " + mNewsItemService.getCount(mContext));
        }
    }

    private void fetchNews() {
        String url = buildNewsFeedUrl();
        // invalidate Volley cache so that a fresh feed is retrieved
        invalidateVolleyCache(url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(LOG_TAG, "response received: " + response.toString());
                //parse response in database
                try {
                    parseResponseIntoDatabase(response);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error parsing news", e);
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
        VolleyRequestQueue.getInstance(mContext).addToRequestQueue(request);
    }

    private String buildNewsFeedUrl() {
        return NEWS_FEED_URL.replace("num=*", "num="+MAX_NEWS_ITEMS);
    }

    private void parseResponseIntoDatabase(JSONObject jsonResponse) throws JSONException, ParseException {
        JSONArray entries = jsonResponse.getJSONObject(NEWS_FEED_JSON_RESPONSE_DATA)
                .getJSONObject(NEWS_FEED_JSON_FEED)
                .getJSONArray(NEWS_FEED_JSON_ENTRIES);

        for (int i=0; i < entries.length(); i++) {
            NewsItem newsItem = convertEntryToNewsItem(entries.getJSONObject(i));
            handleNewsItem(newsItem);
        }
    }

    private void handleNewsItem(NewsItem newsItem) {
        // check if news item already exists in the database
        if ((mNewsItemService.getByLink(mContext, newsItem.getLink())) != null) {
            return;
        }

        // insert new news item into database
        mNewsItemService.insert(mContext, newsItem);
    }

    private NewsItem convertEntryToNewsItem(JSONObject entry) throws JSONException, ParseException {
        NewsItem newsItem = new NewsItem();

        newsItem.setTitle(entry.getString(NEWS_FEED_JSON_ENTRY_TITLE));
        newsItem.setLink(entry.getString(NEWS_FEED_JSON_ENTRY_LINK));
        newsItem.setAuthor(entry.getString(NEWS_FEED_JSON_ENTRY_AUTHOR));

        String dateString = entry.getString(NEWS_FEED_JSON_ENTRY_PUBLISHED_DATE);
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss Z");
        newsItem.setPublishedDate(dateFormat.parse(dateString));

        newsItem.setContentSnippet(entry.getString(NEWS_FEED_JSON_ENTRY_CONTENT_SNIPPET));
        newsItem.setContent(entry.getString(NEWS_FEED_JSON_ENTRY_CONTENT));

        JSONArray categories = entry.getJSONArray(NEWS_FEED_JSON_ENTRY_CATEGORIES);
        for (int i=0; i < categories.length(); i++) {
            newsItem.getCategories().add(categories.getString(i));
        }

        return newsItem;
    }

    private void invalidateVolleyCache(String url) {
        Cache volleyCache = VolleyRequestQueue.getInstance(mContext).getRequestQueue().getCache();
        volleyCache.invalidate(url, true);
    }
}
