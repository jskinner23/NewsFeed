package com.example.jonathanskinner.newsfeed.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by jonathanskinner on 12/14/15.
 */
public interface DataSource {

    public Cursor query(Context context, String dataLocation, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder);

    public int delete(String table, String where, String[] whereArgs);

    public Long insert(String table, ContentValues values);

    public int update(String table, ContentValues values, String where, String[] whereArgs);
}
