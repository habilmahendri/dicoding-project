package com.example.habilmahendri.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {


    private ArrayList<DataCatalog> dataCatalogs;
    private Context context;

    public NowPlayingAdapter(ArrayList<DataCatalog> dataCatalogs, Context context) {
        this.dataCatalogs = dataCatalogs;
        this.context = context;
    }


    @Override
    public NowPlayingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NowPlayingAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tvJudul.setText(dataCatalogs.get(i).getTitle());
        viewHolder.tvOverView.setText(dataCatalogs.get(i).getOverview());
        Double getRating = dataCatalogs.get(i).getVote_average();
        Double starRating = getRating / 2;
        viewHolder.ratingBar.setRating(Float.valueOf(String.valueOf(starRating)));
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/" + dataCatalogs.get(i).getPoster_path())
                .into(viewHolder.imgPoster);
        viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return dataCatalogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ratingCardView)
        RatingBar ratingBar;
        @BindView(R.id.frame_card)
        FrameLayout frameLayout;
        @BindView(R.id.tv_judul)
        TextView tvJudul;
        @BindView(R.id.tv_overview)
        TextView tvOverView;
        @BindView(R.id.img_poster)
        ImageView imgPoster;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }

}
