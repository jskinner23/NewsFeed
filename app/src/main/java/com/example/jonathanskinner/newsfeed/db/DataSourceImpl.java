package com.example.jonathanskinner.newsfeed.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by jonathanskinner on 12/14/15.
 */
public class DataSourceImpl implements DataSource {

    private volatile static DataSourceImpl instance;

    private NewsFeedDbHelper mDbHelper;

    private DataSourceImpl(Context context) {
        mDbHelper = NewsFeedDbHelper.getInstance(context);
    }

    public static DataSourceImpl getInstance(Context context) {
        // "double-checked locking" Singleton pattern to work in multi-threaded environment
        if(instance == null) {
            synchronized (DataSourceImpl.class) {
                if(instance == null) {
                    instance = new DataSourceImpl(context);
                }
            }
        }
        return instance;
    }

    @Override
    public Cursor query(Context context, String dataLocation, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(dataLocation);

        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, groupBy, having, sortOrder);

        return cursor;
    }

    @Override
    public int delete(String table, String where, String[] whereArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted = database.delete(table, where, whereArgs);

        return rowsDeleted;
    }

    @Override
    public Long insert(String table, ContentValues values) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long insertedRowId = database.insert(table, null, values);

        return insertedRowId;
    }

    @Override
    public int update(String table, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(table, values, where, whereArgs);

        return rowsUpdated;
    }
}
