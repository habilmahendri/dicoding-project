package com.example.habilmahendri.cataloguemovie.api;

import com.example.habilmahendri.cataloguemovie.model.DataCatalog;
import com.example.habilmahendri.cataloguemovie.model.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestApiInterface {


    @GET("search/movie")
    Call<JSONResponse> getSearchMovie(@Query("query") String query);

    @GET("movie/now_playing")
    Call<JSONResponse> getNowPlaying();

    @GET("movie/upcoming")
    Call<JSONResponse> getUpComing();

    @GET("movie/upcoming")
    Call<JSONResponse> getUpComingMovie(@Query("api_key") String api_key);

    @GET("movie/{movie_id}/credits")
    Call<JSONResponse> getCast(@Path("movie_id") String movie_id);

    @GET("movie/{movie_id}")
    Call<DataCatalog> getDetail(@Path("movie_id") String movie_id);

}
