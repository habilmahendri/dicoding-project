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
import android.widget.TextView;
import android.widget.Toast;

import com.example.habilmahendri.cataloguemovie.R;
import com.example.habilmahendri.cataloguemovie.activity.DetailFilm;
import com.example.habilmahendri.cataloguemovie.model.DataCatalog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeUpComingAdapter  extends RecyclerView.Adapter<HomeUpComingAdapter.ViewHolder>{

    private ArrayList<DataCatalog> dataCatalogs;
    private Context context;

    public HomeUpComingAdapter(ArrayList<DataCatalog> dataCatalogs, Context context) {
        this.dataCatalogs = dataCatalogs;
        this.context = context;
    }


    @Override
    public HomeUpComingAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_home_upcoming, viewGroup, false);
        return new HomeUpComingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeUpComingAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(dataCatalogs.get(i).getTitle());
        viewHolder.tvDate.setText(dataCatalogs.get(i).getRelease_date());
        
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/" + dataCatalogs.get(i).getPoster_path())
                .into(viewHolder.imageView);
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
    }


    @Override
    public int getItemCount() {

        //limit recyclerview -15
        return dataCatalogs.size()-15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_titleUpComing)TextView title;
        @BindView(R.id.imgPosterPatchUpComing)
        ImageView imageView;
        @BindView(R.id.tv_dateUpComing)
        TextView tvDate;
        @BindView(R.id.cv_upComing)
        CardView cardView;
        
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }
}
