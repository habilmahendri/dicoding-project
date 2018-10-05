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
import com.example.habilmahendri.cataloguemovie.adapter.UpComingAdapter;
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
public class UpComingFragment extends Fragment {

    private ArrayList<DataCatalog> data;
    private UpComingAdapter adapter;
    private static final String TAG = "DATA JSON";
    private Call<JSONResponse> apiCall;
    private ApiClient apiClient = new ApiClient();
    @BindView(R.id.cardView_Upcoming)
    RecyclerView recyclerView;
    @BindView(R.id.pgbar_upcoming)
    ProgressBar pgbarUpcoming;

    public UpComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, view);
        initView();

        if (savedInstanceState != null) {
            ArrayList<DataCatalog> list;
            data = savedInstanceState.getParcelableArrayList("up_coming");

            if (data == null) {
                getData();
            } else {
                pgbarUpcoming.setVisibility(View.GONE);
                adapter = new UpComingAdapter(data, getActivity());
                recyclerView.setAdapter(adapter);
            }
        } else {
            getData();

        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (data == null) {
            getData();
        } else {
            outState.putParcelableArrayList("up_coming", new ArrayList<>(data));
            pgbarUpcoming.setVisibility(View.GONE);

        }

    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    }


    public void getData() {
        apiCall = apiClient.getService().getUpComing();
        apiCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));


                for (int i = 0; i < data.size(); i++) {
                    DataCatalog p = data.get(i);
                    Log.i(TAG, "get UpComing : " + p.getTitle());
                }

                pgbarUpcoming.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.VISIBLE);
                adapter = new UpComingAdapter(data, getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }

}
