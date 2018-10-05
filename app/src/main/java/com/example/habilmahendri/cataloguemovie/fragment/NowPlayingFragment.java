package com.example.habilmahendri.cataloguemovie.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.habilmahendri.cataloguemovie.R;

import com.example.habilmahendri.cataloguemovie.adapter.NowPlayingAdapter;
import com.example.habilmahendri.cataloguemovie.api.ApiClient;
import com.example.habilmahendri.cataloguemovie.model.DataCatalog;
import com.example.habilmahendri.cataloguemovie.model.JSONResponse;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {

    private ArrayList<DataCatalog> data;
    private NowPlayingAdapter adapter;
    private static final String TAG = "DATA JSON";
    private Call<JSONResponse> apiCall;
    private ApiClient apiClient = new ApiClient();
    @BindView(R.id.cardView_nowPlaying)
    RecyclerView recyclerView;
    @BindView(R.id.pgbar_nowPlaying)ProgressBar pgBarNowPlaying;



    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nowplaying, container, false);
        ButterKnife.bind(this, view);
        initView();

        if (savedInstanceState != null) {
            ArrayList<DataCatalog> list;
            pgBarNowPlaying.setVisibility(View.GONE);
            data = savedInstanceState.getParcelableArrayList("now_playing");


            if (data == null) {
                getData();
            }else{
                adapter = new NowPlayingAdapter(data, getActivity());
                recyclerView.setAdapter(adapter);
            }

        } else {
            getData();

        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (data == null) {
            getData();
        }else{
        outState.putParcelableArrayList("now_playing", new ArrayList<>(data));
        }
    }

    //initView
    private void initView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    }


    //untuk getDataNowPlaying
    public void getData() {
        apiCall = apiClient.getService().getNowPlaying();
        apiCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));


                for (int i = 0; i < data.size(); i++) {
                    DataCatalog p = data.get(i);
                    Log.i(TAG, "get Now Playing : " + p.getTitle());
                }
                pgBarNowPlaying.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter = new NowPlayingAdapter(data, getActivity());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
            }
        });
    }

}
