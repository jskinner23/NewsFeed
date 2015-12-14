package com.example.jonathanskinner.newsfeed.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jonathanskinner.newsfeed.resolver.NewsFeedContentResolverConstants.*;

/**
 * Created by jonathanskinner on 12/14/15.
 */
public final class NewsFeedDbHelper extends SQLiteOpenHelper {

    private volatile static NewsFeedDbHelper instance;

    private static final String DATABASE_NAME = "newsfeed.db";
    private static final int DATABASE_VERSION = 1;

    // SQL commands //
    // Create NewsItem Table
    private static final String NEWS_ITEM_TABLE_CREATE = "CREATE TABLE "+ NewsItemResolver.TABLE_NAME+
            " ("+NewsItemResolver._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NewsItemResolver.TITLE + " TEXT, " +
            NewsItemResolver.LINK + " TEXT, " +
            NewsItemResolver.GUID + " TEXT, " +
            NewsItemResolver.CATEGORY + " TEXT, " +
            NewsItemResolver.PUBLISH_DATE + " INTEGER, " +
            NewsItemResolver.DESCRIPTION + " TEXT)";

    public NewsFeedDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public NewsFeedDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    private NewsFeedDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static NewsFeedDbHelper getInstance(Context context) {
        // "double-checked locking" Singleton pattern to work in multi-threaded environment
        if(instance == null) {
            synchronized (NewsFeedDbHelper.class) {
                if(instance == null) {
                    instance = new NewsFeedDbHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create News Item Table
        db.execSQL(NEWS_ITEM_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
