package com.example.habilmahendri.cataloguemovie.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habilmahendri.cataloguemovie.activity.HomeActivity;
import com.example.habilmahendri.cataloguemovie.R;
import com.example.habilmahendri.cataloguemovie.adapter.NowPlayingAdapter;
import com.example.habilmahendri.cataloguemovie.adapter.SearchAdapter;
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
public class SearchFragment extends Fragment {

    private ArrayList<DataCatalog> data;
    private SearchAdapter adapter;
    private static final String TAG = "DATA JSON";
    private Call<JSONResponse> apiCall;
    private ApiClient apiClient = new ApiClient();


    @BindView(R.id.pg_loading)
    ProgressBar pgBar;
    @BindView(R.id.tv_noData)
    TextView tv_nodata;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;
    String myDataFromActivity;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //inisialisasi object dari homeactivity
        HomeActivity activity = (HomeActivity) getActivity();
        myDataFromActivity = activity.getMyData();

        ButterKnife.bind(this, view);

        pgBar.setVisibility(View.GONE);

        initView();

        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelableArrayList("search");

            if (data == null) {
                getData();
            } else {
                adapter = new SearchAdapter(data, getActivity());
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
            outState.putParcelableArrayList("search", new ArrayList<>(data));
        }
    }

    private void initView() {
        pgBar.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.home, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    public void getData() {
        boolean isEmptyFields = false;
        if (TextUtils.isEmpty(myDataFromActivity)) {
            isEmptyFields = true;

        }
        if (!isEmptyFields) {
            pgBar.setVisibility(View.VISIBLE);
            apiCall = apiClient.getService().getSearchMovie(myDataFromActivity);
            apiCall.enqueue(new Callback<JSONResponse>() {
                @Override
                public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                    JSONResponse jsonResponse = response.body();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));


                    for (int i = 0; i < data.size(); i++) {
                        DataCatalog p = data.get(i);
                        Log.i(TAG, "get Search : " + p.getTitle());
                    }
                    pgBar.setVisibility(View.GONE);
                    adapter = new SearchAdapter(data, getActivity());
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<JSONResponse> call, Throwable t) {

                }
            });

        }

    }


}
