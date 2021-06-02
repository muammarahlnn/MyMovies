package com.ardnn.mymovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie extends Film implements Parcelable {
    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    // parcelable methods ---------------------------------------------
    protected Movie(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        synopsis = in.readString();
        posterUrl = in.readString();
        wallpaperUrl = in.readString();
        vote = in.readDouble();
        genreIdList = new ArrayList<>();
        in.readList(genreIdList, Integer.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(synopsis);
        dest.writeString(posterUrl);
        dest.writeString(wallpaperUrl);
        dest.writeDouble(vote);
        dest.writeList(genreIdList);
    }

    // getter and setter ----------------------------------------
    @Override
    public String getName() {
        return title;
    }

    @Override
    public void setName(String name) {
        this.title = name;
    }

    @Override
    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void setReleaseDate(String date) {
        releaseDate = date;
    }

}
