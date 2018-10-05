package com.example.myfavoriteapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfavoriteapp.R;
import com.squareup.picasso.Picasso;

import static com.example.myfavoriteapp.db.DatabaseContract.NoteColumns.DATE;
import static com.example.myfavoriteapp.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static com.example.myfavoriteapp.db.DatabaseContract.NoteColumns.IMG;
import static com.example.myfavoriteapp.db.DatabaseContract.NoteColumns.TITLE;
import static com.example.myfavoriteapp.db.DatabaseContract.getColumnString;

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c, boolean autoReqeury) {
        super(context, c, autoReqeury);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, viewGroup, false);

        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_item_title);
            TextView tvDate = (TextView) view.findViewById(R.id.tv_item_date);
            TextView tvDescription = (TextView) view.findViewById(R.id.tv_item_description);
            ImageView img = (ImageView) view.findViewById(R.id.img_fav);

            tvTitle.setText(getColumnString(cursor,TITLE));
            tvDescription.setText(getColumnString(cursor, DESCRIPTION));
            tvDate.setText(getColumnString(cursor,DATE));

            Picasso.get()
                    .load("http://image.tmdb.org/t/p/w185/" + getColumnString(cursor,IMG))
                    .into(img);

        }


    }
}
