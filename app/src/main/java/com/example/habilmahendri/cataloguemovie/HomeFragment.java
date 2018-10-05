package com.example.habilmahendri.cataloguemovie;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habilmahendri.cataloguemovie.adapter.HomeNowPlayingAdapter;
import com.example.habilmahendri.cataloguemovie.adapter.HomeUpComingAdapter;
import com.example.habilmahendri.cataloguemovie.adapter.NowPlayingAdapter;
import com.example.habilmahendri.cataloguemovie.api.ApiClient;
import com.example.habilmahendri.cataloguemovie.fragment.NowPlayingFragment;
import com.example.habilmahendri.cataloguemovie.fragment.SearchFragment;
import com.example.habilmahendri.cataloguemovie.fragment.UpComingFragment;
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
public class HomeFragment extends Fragment {

    private ArrayList<DataCatalog> data;
    private HomeNowPlayingAdapter adapter;
    private HomeUpComingAdapter adapterUpcoming;
    private static final String TAG = "DATA JSON";
    private Call<JSONResponse> apiCall;
    private ApiClient apiClient = new ApiClient();
    @BindView(R.id.cardHome_nowPlaying)
    RecyclerView recyclerView;
    @BindView(R.id.cardHome_upComing)RecyclerView rvHomeUpcoming;
    @BindView(R.id.more_nowPlaying)TextView textViewMore;
    @BindView(R.id.placeHolder)LinearLayout placeHolder;
    @BindView(R.id.placeHolderUpComing)LinearLayout placeHolderUpComing;
    @BindView(R.id.more_UpComing)TextView tvMoreUpComing;


    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        textViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NowPlayingFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .commit();
            }
        });

        tvMoreUpComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UpComingFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,fragment)
                        .commit();
            }
        });
        // Inflate the layout for this fragment

        //initView();
        initViewUpcoming();


        if (savedInstanceState != null) {
            ArrayList<DataCatalog> list;
            placeHolder.setVisibility(View.GONE);
            placeHolderUpComing.setVisibility(View.GONE);

            data = savedInstanceState.getParcelableArrayList("now_playing");
            list = savedInstanceState.getParcelableArrayList("up_coming");

            adapter = new HomeNowPlayingAdapter(data, getActivity());
            recyclerView.setAdapter(adapter);

            adapterUpcoming = new HomeUpComingAdapter(list, getActivity());
            rvHomeUpcoming.setAdapter(adapterUpcoming);
        } else {
            getData();
            //getDataUpcoming();


        }

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (data == null) {
            getData();
            //getDataUpcoming();
        }else{
        outState.putParcelableArrayList("now_playing", new ArrayList<>(data));
        outState.putParcelableArrayList("up_coming", new ArrayList<>(data));
        }
    }



//    private void initView() {
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), GridLayoutManager.HORIZONTAL,false));
//
//
//    }
    private void initViewUpcoming() {
        rvHomeUpcoming.setHasFixedSize(true);
        //rvHomeUpcoming.setLayoutManager(new LinearLayoutManager(getActivity(), GridLayoutManager.HORIZONTAL,false));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


    }

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
                placeHolderUpComing.setVisibility(View.GONE);
                placeHolder.setVisibility(View.GONE);
                adapter = new HomeNowPlayingAdapter(data, getActivity());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }
//    public void getDataUpcoming() {
//        apiCall = apiClient.getService().getUpComing();
//        apiCall.enqueue(new Callback<JSONResponse>() {
//            @Override
//            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
//                JSONResponse jsonResponse = response.body();
//                data = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
//
//
//                for (int i = 0; i < data.size(); i++) {
//                    DataCatalog p = data.get(i);
//                    Log.i(TAG, "get Now Playing : " + p.getTitle());
//                }
//                placeHolderUpComing.setVisibility(View.GONE);
//                adapterUpcoming = new HomeUpComingAdapter(data, getActivity());
//                rvHomeUpcoming.setAdapter(adapterUpcoming);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<JSONResponse> call, Throwable t) {
//
//            }
//        });
//    }

}
