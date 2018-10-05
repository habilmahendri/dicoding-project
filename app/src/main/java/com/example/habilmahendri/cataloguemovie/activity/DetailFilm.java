package com.example.habilmahendri.cataloguemovie.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devs.readmoreoption.ReadMoreOption;
import com.example.habilmahendri.cataloguemovie.R;
import com.example.habilmahendri.cataloguemovie.adapter.CastAdapter;
import com.example.habilmahendri.cataloguemovie.adapter.NowPlayingAdapter;
import com.example.habilmahendri.cataloguemovie.api.ApiClient;
import com.example.habilmahendri.cataloguemovie.db.FavoriteHelper;
import com.example.habilmahendri.cataloguemovie.model.DataCatalog;
import com.example.habilmahendri.cataloguemovie.model.JSONResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CONTENT_URI;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.DATE;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.IDMOVIE;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.IMG;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.OVERVIEW;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.TITLE;

public class DetailFilm extends AppCompatActivity {


    private ArrayList<DataCatalog> data;
    private static final String TAG = "DATA JSON";
    private Call<JSONResponse> apiCall;
    private Call<DataCatalog> apiCallDetail;
    private ApiClient apiClient = new ApiClient();
    private CastAdapter castAdapter;
    public static String EXTRA_DATA = "extra_data";
    public static String IS_FAVORITE = "is_favorite";
    public static String IDSQL = "idsql";

    @BindView(R.id.rv_cast)
    RecyclerView rvCast;
    @BindView(R.id.rating)
    RatingBar ratingBar;
    @BindView(R.id.tv_overView_detail)
    TextView tvOverView;
    @BindView(R.id.img_backdrop_detail)
    ImageView imgBackDrop;
    @BindView(R.id.tv_judul_detail)
    TextView tvtTitle;
    @BindView(R.id.img_poster_detail)
    ImageView imgPoster;
    @BindView(R.id.tv_tanggal_detail)
    TextView tvReleaseDate;
    @BindView(R.id.favButton)
    ImageView buttonFav;
    private FavoriteHelper favoriteHelper;
    private DataCatalog catalog;
    private boolean isFavorite = false;
    private int favorite;
    private String id;
    private int idsql;
    private DataCatalog mDataCatalog;
    private DataCatalog item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        ButterKnife.bind(this);
        //getIncomingIntent();
        mDataCatalog = getIntent().getParcelableExtra(EXTRA_DATA);
        id = mDataCatalog.getId();

        //add
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Uri uri = getIntent().getData();

        initView();


        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) catalog = new DataCatalog(cursor);
                cursor.close();
            }
        }
        favorite = getIntent().getIntExtra(IS_FAVORITE, 0);
        if (favorite == 1) {
            isFavorite = true;
            buttonFav.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) {
                    addFavorite();
                    Toast.makeText(DetailFilm.this, R.string.addtofavorite, Toast.LENGTH_SHORT).show();
                } else {
                    deleteFavorite();
                    Toast.makeText(DetailFilm.this, R.string.deleted, Toast.LENGTH_SHORT).show();
                }


            }
        });

        if (savedInstanceState != null) {

            buttonFav.setVisibility(View.VISIBLE);

            data = savedInstanceState.getParcelableArrayList("now_playing");
            Toast.makeText(this, mDataCatalog.getTitle(), Toast.LENGTH_SHORT).show();

            tvtTitle.setText(mDataCatalog.getTitle());
            tvReleaseDate.setText(mDataCatalog.getRelease_date());
            ReadMoreOption readMoreOption = new ReadMoreOption.Builder(getApplicationContext())
                    .textLength(100)
                    .moreLabel("more")
                    .lessLabel("less")
                    .moreLabelColor(Color.BLACK)
                    .lessLabelColor(Color.BLACK)
                    .build();


            Picasso.get()
                    .load("http://image.tmdb.org/t/p/w780/" + mDataCatalog.getPoster_path())
                    .into(imgPoster);

            Picasso.get()
                    .load("http://image.tmdb.org/t/p/w780/" + mDataCatalog.getBackdrop_path())
                    .into(imgBackDrop);

            readMoreOption.addReadMoreTo(tvOverView, mDataCatalog.getOverview());

            Double getRating = mDataCatalog.getVote_average();
            Double starRating = getRating / 2;
            ratingBar.setRating(Float.valueOf(String.valueOf(starRating)));

            data = savedInstanceState.getParcelableArrayList("cast");
            castAdapter = new CastAdapter(data, DetailFilm.this);
            rvCast.setAdapter(castAdapter);


        } else {
            getDataDetail();

        }


        getCast();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Menyimpan data tertentu (String) ke Bundle
        if (data == null) {
            getDataDetail();
            getCast();
            Toast.makeText(this, "bro bro", Toast.LENGTH_SHORT).show();
        }else{
        savedInstanceState.putParcelableArrayList("now_playing", new ArrayList<>(data));
        savedInstanceState.putParcelableArrayList("cast",new ArrayList<>(data));
        }
        // Selalu simpan pemanggil superclass di bawah agar data di view tetap tersimpan
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Selalu panggil superclass agar data di view tetap ada
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void addFavorite() {
        ContentValues values = new ContentValues();
        values.put(TITLE, mDataCatalog.getTitle());
        values.put(OVERVIEW, mDataCatalog.getOverview());
        values.put(DATE, mDataCatalog.getRelease_date());
        values.put(IMG, mDataCatalog.getPoster_path());
        values.put(IDMOVIE, id);
        getContentResolver().insert(CONTENT_URI, values);
        finish();
    }

    private void deleteFavorite() {
        idsql = getIntent().getIntExtra(IDSQL, 0);
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + idsql),
                null,
                null);
        Intent intent = new Intent(DetailFilm.this, HomeActivity.class);
        startActivity(intent);

    }

    private void initView() {
        rvCast.setHasFixedSize(true);
        rvCast.setLayoutManager(new LinearLayoutManager(this, GridLayoutManager.HORIZONTAL, false));


    }

    public void getCast() {
        apiCall = apiClient.getService().getCast(id);
        apiCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getCast()));


                for (int i = 0; i < data.size(); i++) {
                    DataCatalog p = data.get(i);
                    Log.i(TAG, "get cast : " + p.getName());
                }
                castAdapter = new CastAdapter(data, DetailFilm.this);
                rvCast.setAdapter(castAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }


    private void getDataDetail() {
        apiCallDetail = apiClient.getService().getDetail(id);
        apiCallDetail.enqueue(new Callback<DataCatalog>() {
            @Override
            public void onResponse(Call<DataCatalog> call, Response<DataCatalog> response) {
                if (response.isSuccessful()) {
                    item = response.body();

                    Log.i(TAG, "get detail : " + item.getTitle());
                    buttonFav.setVisibility(View.VISIBLE);
                    ReadMoreOption readMoreOption = new ReadMoreOption.Builder(getApplicationContext())
                            .textLength(100)
                            .moreLabel("more")
                            .lessLabel("less")
                            .moreLabelColor(Color.BLACK)
                            .lessLabelColor(Color.BLACK)
                            .build();

                    readMoreOption.addReadMoreTo(tvOverView, item.getOverview());


                    tvtTitle.setText(item.getTitle());


                    Double getRating = item.getVote_average();
                    Double starRating = getRating / 2;
                    ratingBar.setRating(Float.valueOf(String.valueOf(starRating)));
                    tvReleaseDate.setText(item.getRelease_date());

                    Picasso.get()
                            .load("http://image.tmdb.org/t/p/w780/" + item.getPoster_path())
                            .into(imgPoster);
                    Picasso.get()
                            .load("http://image.tmdb.org/t/p/w780/" + item.getBackdrop_path())
                            .into(imgBackDrop);
                }
            }

            @Override
            public void onFailure(Call<DataCatalog> call, Throwable t) {
            }
        });
    }


}
