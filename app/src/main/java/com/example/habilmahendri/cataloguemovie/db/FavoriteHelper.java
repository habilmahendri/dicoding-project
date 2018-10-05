package com.example.habilmahendri.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.habilmahendri.cataloguemovie.model.DataCatalog;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.DATE;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.IMG;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.OVERVIEW;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.TITLE;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.TABLE_CATALOG;

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_CATALOG;
    public Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open()throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<DataCatalog> query() {
        ArrayList<DataCatalog> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        DataCatalog catalog;
        if (cursor.getCount() > 0) {
            do {
                catalog = new DataCatalog();
                catalog.setIdMovie(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                catalog.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                catalog.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                catalog.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                catalog.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(IMG)));

                arrayList.add(catalog);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(DataCatalog catalog) {
        ContentValues initialValue = new ContentValues();
        initialValue.put(TITLE, catalog.getTitle());
        initialValue.put(OVERVIEW, catalog.getOverview());
        initialValue.put(DATE, catalog.getRelease_date());
        initialValue.put(IMG,catalog.getPoster_path());
        return database.insert(DATABASE_TABLE, null, initialValue);
    }

    public int update(DataCatalog catalog) {
        ContentValues args = new ContentValues();
        args.put(TITLE, catalog.getTitle());
        args.put(OVERVIEW, catalog.getOverview());
        args.put(DATE, catalog.getRelease_date());
        args.put(IMG,catalog.getPoster_path());

        return database.update(DATABASE_TABLE,args, _ID + "= '" +catalog.getId()+"'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_CATALOG, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }
    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " + ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
