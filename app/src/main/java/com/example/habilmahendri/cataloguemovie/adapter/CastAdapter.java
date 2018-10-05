package com.example.habilmahendri.cataloguemovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.habilmahendri.cataloguemovie.R;
import com.example.habilmahendri.cataloguemovie.model.DataCatalog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private ArrayList<DataCatalog> dataCatalogs;
    private Context context;

    public CastAdapter(ArrayList<DataCatalog>dataCatalogs, Context context) {
        this.dataCatalogs = dataCatalogs;
        this.context = context;
    }

    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_home_upcoming, viewGroup, false);
        return new CastAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(dataCatalogs.get(i).getName());
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/"+dataCatalogs.get(i).getProfile_path())
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataCatalogs.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.tv_titleUpComing)TextView name;
        @BindView(R.id.imgPosterPatchUpComing)ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
