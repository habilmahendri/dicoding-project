package com.example.habilmahendri.cataloguemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.DATE;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.IDMOVIE;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.IMG;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.OVERVIEW;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.CatalogColumns.TITLE;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.getColumnInt;
import static com.example.habilmahendri.cataloguemovie.db.DatabaseContract.getColumnString;

public class DataCatalog implements Parcelable {

    private String title;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private String adult;
    private String release_date;
    private Double vote_average;
    private String name;
    private String profile_path;
    private String id;
    private int idMovie;

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.adult);
        dest.writeString(this.release_date);
        dest.writeValue(this.vote_average);
        dest.writeString(this.name);
        dest.writeString(this.profile_path);
        dest.writeString(this.id);
    }

    public DataCatalog() {
    }

    public DataCatalog(Cursor cursor) {
        this.idMovie = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor,TITLE);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.release_date = getColumnString(cursor, DATE);
        this.poster_path = getColumnString(cursor, IMG);
        this.id = getColumnString(cursor, IDMOVIE);

    }
    protected DataCatalog(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.adult = in.readString();
        this.release_date = in.readString();
        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
        this.name = in.readString();
        this.profile_path = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<DataCatalog> CREATOR = new Parcelable.Creator<DataCatalog>() {
        @Override
        public DataCatalog createFromParcel(Parcel source) {
            return new DataCatalog(source);
        }

        @Override
        public DataCatalog[] newArray(int size) {
            return new DataCatalog[size];
        }
    };
}
