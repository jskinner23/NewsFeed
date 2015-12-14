package com.example.jonathanskinner.newsfeed.resolver;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jonathanskinner on 12/14/15.
 */
public class NewsFeedContentResolverConstants {

    // This class cannot be instantiated
    private NewsFeedContentResolverConstants() {}

    // News Item Table
    public static class NewsItemResolver implements BaseColumns {

        //This class cannot be instantiated
        private NewsItemResolver(){}

        private static final String PATH = "news_item";

        public static final String TABLE_NAME = "news_item";

        public static final String TITLE = "title";
        public static final String LINK = "link";
        public static final String GUID = "guid";
        public static final String CATEGORY = "category";
        public static final String PUBLISH_DATE = "publishDate";
        public static final String DESCRIPTION = "description";

        public static Uri getCONTENT_URI(String authority) {
            return buildUri(authority, PATH);
        }

        // Created this getter so to distinguish 'path' from the column resolver constants
        public static String getPath() {
            return PATH;
        }
    }

    private static Uri buildUri(String authority, String path) {
        Uri AUTHORITY_URI = Uri.parse("content://" + authority);
        return Uri.withAppendedPath(AUTHORITY_URI, path);
    }
}
