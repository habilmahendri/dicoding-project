package com.example.habilmahendri.cataloguemovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habilmahendri.cataloguemovie.R;
import com.example.habilmahendri.cataloguemovie.activity.DetailFilm;
import com.example.habilmahendri.cataloguemovie.activity.HomeActivity;
import com.example.habilmahendri.cataloguemovie.model.DataCatalog;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Cursor listFav;
    private Activity activity;
    private Context context;

    public FavoriteAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void setListFav(Cursor listFav) {
        this.listFav = listFav;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final DataCatalog catalog = getItem(position);
        holder.tvTitle.setText(catalog.getTitle());
        holder.tvDate.setText(catalog.getRelease_date());
        holder.tvOverview.setText(catalog.getOverview());
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w780/" + catalog.getPoster_path())
                .into(holder.imgFav);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.getContentResolver().delete(
                        Uri.parse(CONTENT_URI + "/" + catalog.getIdMovie()),
                        null,
                        null);
                Toast.makeText(activity, R.string.deleted, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            }
        });
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataCatalog mData = new DataCatalog();
//                mData.setId(catalog.getId());
//                Intent intent = new Intent(context, DetailFilm.class);
//                intent.putExtra(DetailFilm.IS_FAVORITE, 1);
//                intent.putExtra(DetailFilm.IDSQL, catalog.getIdMovie());
//                intent.putExtra(DetailFilm.EXTRA_DATA, mData);
//                context.startActivity(intent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (listFav == null) return 0;
        return listFav.getCount();
    }

    private DataCatalog getItem(int position) {
        if (!listFav.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new DataCatalog(listFav);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_description)
        TextView tvOverview;
        @BindView(R.id.tv_item_date)
        TextView tvDate;
        @BindView(R.id.cv_item_note)
        CardView cardView;
        @BindView(R.id.btnDelete)
        ImageView delete;
        @BindView(R.id.img_fav)
        ImageView imgFav;


        public ViewHolder(View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}
