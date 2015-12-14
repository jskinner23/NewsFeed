package com.example.jonathanskinner.newsfeed.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jonathanskinner.newsfeed.db.DataSourceImpl;
import com.example.jonathanskinner.newsfeed.resolver.NewsFeedContentResolverConstants.*;

/**
 * Created by jonathanskinner on 12/14/15.
 */
public class NewsFeedContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(0);

    //URI codes
    private static final int URI_CODE_NEWS_ITEM = 1;

    public static final String AUTHORITY = "com.example.jonathanskinner.newsfeed.provider.newsfeedcontentprovider";
    private DataSourceImpl mDataSource;

    public NewsFeedContentProvider() {
    }

    @Override
    public boolean onCreate() {
        initUriMatchers();
        mDataSource = DataSourceImpl.getInstance(getContext());
        return true;
    }

    private void initUriMatchers() {
        sUriMatcher.addURI(AUTHORITY, NewsItemResolver.getPath(), URI_CODE_NEWS_ITEM);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String dataLocation = getDataLocation(uri);
        Cursor cursor = mDataSource.query(getContext(), dataLocation, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String dataLocation = getDataLocation(uri);
        Long insertedRowId = mDataSource.insert(dataLocation, values);
        if(insertedRowId > 0) {
            Uri newRowUri = ContentUris.withAppendedId(uri, insertedRowId);
            getContext().getContentResolver().notifyChange(uri, null);
            return newRowUri;
        } else {
            Log.i("ContentProvider", "returning null");
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String dataLocation = getDataLocation(uri);
        int rowsDeleted = mDataSource.delete(dataLocation, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String dataLocation = getDataLocation(uri);
        int rowsUpdated = mDataSource.update(dataLocation, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * @param uri
     * @return the table described by the URI; null if no table matches the URI
     */
    private String getDataLocation(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case URI_CODE_NEWS_ITEM:
                return NewsItemResolver.TABLE_NAME;
            default:
                throw new IllegalArgumentException("No matching table found for URI: " + uri);
        }
    }
}
