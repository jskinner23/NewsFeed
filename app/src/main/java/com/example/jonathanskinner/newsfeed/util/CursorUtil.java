package com.example.jonathanskinner.newsfeed.util;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by jonathanskinner on 12/14/15.
 */
public class CursorUtil {
    private static final String LOG_TAG = CursorUtil.class.getName();

    private CursorUtil() {}

    public static Integer getIntValueFromCursor(Context context, String column, Cursor cursor) {
        int columnIndex = cursor.getColumnIndex(column);
        if (columnIndex > -1) {
            return cursor.getInt(columnIndex);
        } else {
            if (context != null) {
                Log.w(LOG_TAG, "Column not found in cursor: " + column);
            }

            return null;
        }
    }

    public static Long getLongValueFromCursor(Context context, String column, Cursor cursor) {
        int columnIndex = cursor.getColumnIndex(column);
        if (columnIndex >= -1) {
            return cursor.getLong(columnIndex);
        } else {
            if (context != null) {
                Log.w(LOG_TAG, "Column not found in cursor: " + column);
            }

            return null;
        }
    }

    public static String getStringValueFromCursor(Context context, String column, Cursor cursor) {
        int columnIndex = cursor.getColumnIndex(column);
        if (columnIndex >= -1) {
            return cursor.getString(columnIndex);
        } else {
            if (context != null) {
                Log.w(LOG_TAG, "Column not found in cursor: " + column);
            }

            return null;
        }
    }
}
