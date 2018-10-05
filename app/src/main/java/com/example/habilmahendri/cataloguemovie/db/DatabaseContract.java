package com.example.habilmahendri.cataloguemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_CATALOG = "catalog";

    public static final class CatalogColumns implements BaseColumns {


        public static String TITLE = "title";

        public static String OVERVIEW = "overview";

        public static String DATE = "date";

        public static String IMG = "image";

        public static String IDMOVIE = "idmovie";

    }

    public static String AUTHORITY = "com.example.habilmahendri.cataloguemovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_CATALOG)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));

    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
