package com.example.myfavoriteapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myfavoriteapp.adapter.FavoriteAdapter;
import com.example.myfavoriteapp.db.DatabaseContract;

import static com.example.myfavoriteapp.db.DatabaseContract.NoteColumns.CONTENT_URI;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private FavoriteAdapter dicodingNotesAdapter;
    ListView lvNotes;

    private final int LOAD_NOTES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Favorite Film");

        lvNotes = (ListView) findViewById(R.id.lv_notes);
        dicodingNotesAdapter = new FavoriteAdapter(this, null, true);
        lvNotes.setAdapter(dicodingNotesAdapter);
        //lvNotes.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dicodingNotesAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        dicodingNotesAdapter.swapCursor(null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
