package com.example.jonathanskinner.newsfeed.service.impl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.jonathanskinner.newsfeed.model.NewsItem;
import com.example.jonathanskinner.newsfeed.provider.NewsFeedContentProvider;
import com.example.jonathanskinner.newsfeed.resolver.NewsFeedContentResolverConstants.NewsItemResolver;
import com.example.jonathanskinner.newsfeed.service.NewsItemService;
import com.example.jonathanskinner.newsfeed.util.CursorUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by jonathanskinner on 12/14/15.
 */
public class NewsItemServiceImpl implements NewsItemService {

    @Override
    public List<NewsItem> getAll(Context context) {
        List<NewsItem> newsItemsList = new ArrayList<NewsItem>();

        Cursor cursor = runQuery(context, null, null, null, null);

        // convert cursor to NewsItem list
        return getNewsItemListFromCursor(context, cursor);
    }

    @Override
    public NewsItem getById(Context context, Long id) {
        //make query
        String selection = NewsItemResolver._ID+" = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = runQuery(context, null, selection, selectionArgs, null);

        // convert cursor to NewsItem
        return getNewsItemFromCursor(context, cursor);
    }

    @Override
    public NewsItem getByLink(Context context, String link) {
        //make query
        String selection = NewsItemResolver.LINK+" = ?";
        String[] selectionArgs = { link };

        Cursor cursor = runQuery(context, null, selection, selectionArgs, null);

        // convert cursor to NewsItem
        return getNewsItemFromCursor(context, cursor);
    }

    @Override
    public Integer getCount(Context context) {
        Cursor cursor = runQuery(context, null, null, null, null);
        if (cursor == null) {
            return 0;
        }
        Integer count = cursor.getCount();
        cursor.close();
        return count;
    }

    @Override
    public Uri insert(Context context, NewsItem newsItem) {
        return context.getContentResolver().insert(NewsItemResolver.getCONTENT_URI(NewsFeedContentProvider.AUTHORITY),
                newsItem.getContentValues());
    }

    @Override
    public Integer update(Context context, NewsItem newsItem) {
        String where = NewsItemResolver._ID+" = ?";
        String[] selectionArgs = { String.valueOf(newsItem.getId()) };
        int numUpdated = context.getContentResolver().update(NewsItemResolver.getCONTENT_URI(NewsFeedContentProvider.AUTHORITY),
                newsItem.getContentValues(), where, selectionArgs);
        if(numUpdated==0) {
            insert(context, newsItem);
            return 1;
        } else {
            return numUpdated;
        }
    }

    @Override
    public Integer delete(Context context, NewsItem newsItem) {
        // delete news item
        String where = NewsItemResolver._ID + " = ?";
        String[] selectionArgs = { String.valueOf(newsItem.getId()) };

        return performDelete(context, where, selectionArgs);
    }

    @Override
    public Integer deleteNewsItems(Context context, List<NewsItem> newsItemsToDelete) {
        StringBuilder newsItemIdList = new StringBuilder();
        for (NewsItem newsItem : newsItemsToDelete) {
            newsItemIdList.append(newsItem.getId());
            if (!newsItem.getId().equals(newsItemsToDelete.size()-1)) {
                newsItemIdList.append(",");
            }
        }

        // delete news item
        String where = NewsItemResolver._ID + " IN (?)";
        String[] selectionArgs = { newsItemIdList.toString() };

        return performDelete(context, where, selectionArgs);
    }

    private Cursor runQuery(Context context, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return context.getContentResolver().query(NewsItemResolver.getCONTENT_URI(NewsFeedContentProvider.AUTHORITY),
                projection, selection, selectionArgs, sortOrder);
    }

    private Integer performDelete(Context context, String where, String[] selectionArgs) {
        return context.getContentResolver().delete(NewsItemResolver.getCONTENT_URI(NewsFeedContentProvider.AUTHORITY),
                where, selectionArgs);
    }

    private List<NewsItem> getNewsItemListFromCursor(Context context, Cursor cursor) {
        cursor.moveToFirst();
        List<NewsItem> newsItemsList = convertCursorToNewsItemList(context, cursor);
        cursor.close();

        return newsItemsList;
    }

    private NewsItem getNewsItemFromCursor(Context context, Cursor cursor) {
        if(cursor==null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        NewsItem newsItem = convertCursorToNewsItem(context, cursor);
        cursor.close();

        return newsItem;
    }

    private List<NewsItem> convertCursorToNewsItemList(Context context, Cursor cursor) {
        List<NewsItem> newsItemsList = new ArrayList<NewsItem>();

        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                newsItemsList.add(convertCursorToNewsItem(context, cursor));
                cursor.moveToNext();
            }
        }

        return newsItemsList;
    }

    private NewsItem convertCursorToNewsItem(Context context, Cursor cursor) {
        NewsItem newsItem = new NewsItem();

        newsItem.setId(CursorUtil.getLongValueFromCursor(context, NewsItemResolver._ID, cursor));
        newsItem.setTitle(CursorUtil.getStringValueFromCursor(context, NewsItemResolver.TITLE, cursor));
        newsItem.setLink(CursorUtil.getStringValueFromCursor(context, NewsItemResolver.LINK, cursor));
        newsItem.setAuthor(CursorUtil.getStringValueFromCursor(context, NewsItemResolver.AUTHOR, cursor));
        Long publishedDateMilliseconds = CursorUtil.getLongValueFromCursor(context, NewsItemResolver.PUBLISHED_DATE, cursor);
        newsItem.setPublishedDate(publishedDateMilliseconds != null ? new Date(publishedDateMilliseconds) : null);
        newsItem.setContentSnippet(CursorUtil.getStringValueFromCursor(context, NewsItemResolver.CONTENT_SNIPPET, cursor));
        newsItem.setContent(CursorUtil.getStringValueFromCursor(context, NewsItemResolver.CONTENT, cursor));
        String categories = CursorUtil.getStringValueFromCursor(context, NewsItemResolver.CATEGORIES, cursor);
        newsItem.setCategories(Arrays.asList(categories.split(",")));

        return newsItem;
    }
}
