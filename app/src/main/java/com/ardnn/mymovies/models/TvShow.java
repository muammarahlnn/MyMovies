package com.ardnn.mymovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShow extends Film implements Parcelable {
    @SerializedName("name")
    private String name;

    @SerializedName("first_air_date")
    private String firstAiring;

    // parcelable methods ---------------------------------------------------
    protected TvShow(Parcel in) {
        name = in.readString();
        firstAiring = in.readString();
        synopsis = in.readString();
        posterUrl = in.readString();
        wallpaperUrl = in.readString();
        vote = in.readDouble();
        genreIdList = new ArrayList<>();
        in.readList(genreIdList, Integer.class.getClassLoader());
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(firstAiring);
        dest.writeString(synopsis);
        dest.writeString(posterUrl);
        dest.writeString(wallpaperUrl);
        dest.writeDouble(vote);
        dest.writeList(genreIdList);
    }

    // getter and setter -------------------------------
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getReleaseDate() {
        return firstAiring;
    }

    @Override
    public void setReleaseDate(String date) {
        this.firstAiring = date;
    }

}
