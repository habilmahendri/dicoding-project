package com.example.habilmahendri.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habilmahendri.cataloguemovie.R;
import com.example.habilmahendri.cataloguemovie.activity.DetailFilm;
import com.example.habilmahendri.cataloguemovie.model.DataCatalog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeNowPlayingAdapter extends RecyclerView.Adapter<HomeNowPlayingAdapter.ViewHolder>{

    private ArrayList<DataCatalog> dataCatalogs;
    private Context context;

    public HomeNowPlayingAdapter(ArrayList<DataCatalog> dataCatalogs, Context context) {
        this.dataCatalogs = dataCatalogs;
        this.context = context;
    }


    @Override
    public HomeNowPlayingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_home_nowplaying, viewGroup, false);
        return new HomeNowPlayingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeNowPlayingAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(dataCatalogs.get(i).getTitle());
        viewHolder.date.setText(dataCatalogs.get(i).getRelease_date());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCatalog mData = new DataCatalog();
                mData.setId(dataCatalogs.get(i).getId());
                mData.setTitle(dataCatalogs.get(i).getTitle());
                mData.setPoster_path(dataCatalogs.get(i).getPoster_path());
                mData.setBackdrop_path(dataCatalogs.get(i).getBackdrop_path());
                mData.setOverview(dataCatalogs.get(i).getOverview());
                mData.setVote_average(dataCatalogs.get(i).getVote_average());
                mData.setRelease_date(dataCatalogs.get(i).getRelease_date());

                Intent intent = new Intent(context, DetailFilm.class);
                intent.putExtra(DetailFilm.EXTRA_DATA, mData);
                context.startActivity(intent);
            }
        });
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w780/" + dataCatalogs.get(i).getBackdrop_path())
                .into(viewHolder.imageView);
    }


    @Override
    public int getItemCount() {
        return dataCatalogs.size()-15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_homeNowPlaying)CardView cardView;
        @BindView(R.id.tv_title)TextView title;
        @BindView(R.id.tv_date)TextView date;
        @BindView(R.id.imgPosterPatch)
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }
}
