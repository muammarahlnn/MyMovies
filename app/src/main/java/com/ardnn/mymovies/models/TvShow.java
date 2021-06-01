package com.ardnn.mymovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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
        dest.writeString(posterUrl);
        dest.writeString(wallpaperUrl);
        dest.writeString(synopsis);
        dest.writeString(firstAiring);
        dest.writeDouble(vote);
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
