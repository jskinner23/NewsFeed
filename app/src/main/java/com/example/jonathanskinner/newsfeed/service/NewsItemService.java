package com.example.jonathanskinner.newsfeed.service;

import android.content.Context;
import android.net.Uri;

import com.example.jonathanskinner.newsfeed.model.NewsItem;

import java.util.List;

/**
 * Created by jonathanskinner on 12/14/15.
 */
public interface NewsItemService {

    /**
     * Gets all NewsItems in the database
     *
     * @param context
     * @return
     */
    public List<NewsItem> getAll(Context context);

    /**
     * Gets a NewsItem by its database ID
     *
     * @param context
     * @param id
     * @return NewsItem if found; null if not found
     */
    public NewsItem getById(Context context, Long id);

    /**
     * Gets a NewsItem by its link
     *
     * @param context
     * @param link
     * @return NewsItem if found; null if not found
     */
    public NewsItem getByLink(Context context, String link);

    /**
     * Gets a count of all NewsItems in the database
     * @param context
     *
     * @return Count of all NewsItems in the database
     */
    public Integer getCount(Context context);

    /**
     * Inserts a NewsItem into the database
     *
     * @param context
     * @param newsItem
     * @return the URI for the new NewsItem
     */
    public Uri insert(Context context, NewsItem newsItem);

    /**
     * Updates a NewsItem in the database
     *
     * @param context
     * @param newsItem
     * @return the number of rows in the database that were updated
     */
    public Integer update(Context context, NewsItem newsItem);

    /**
     * Deletes a NewsItem from the database
     *
     * @param context
     * @param newsItem
     * @return the number of rows in the database that were deleted
     */
    public Integer delete(Context context, NewsItem newsItem);

    /**
     * Deletes all of the News Items in the list
     *
     * @param context
     * @param newsItemsToDelete
     * @return the number of rows in the database that were deleted
     */
    public Integer deleteNewsItems(Context context, List<NewsItem> newsItemsToDelete);
}
