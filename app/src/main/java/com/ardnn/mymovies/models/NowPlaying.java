package com.ardnn.mymovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NowPlaying extends Movie implements Parcelable {
    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    // parcelable methods ---------------------------------------------

    protected NowPlaying(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        synopsis = in.readString();
        imageUrl = in.readString();
        vote = in.readDouble();
    }

    public static final Creator<NowPlaying> CREATOR = new Creator<NowPlaying>() {
        @Override
        public NowPlaying createFromParcel(Parcel in) {
            return new NowPlaying(in);
        }

        @Override
        public NowPlaying[] newArray(int size) {
            return new NowPlaying[size];
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
        dest.writeString(imageUrl);
        dest.writeDouble(vote);
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
