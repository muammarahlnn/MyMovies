package com.ardnn.mymovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AiringToday extends Movie implements Parcelable {
    @SerializedName("name")
    private String name;

    @SerializedName("first_air_date")
    private String firstAiring;

    // parcelable methods ---------------------------------------------------
    protected AiringToday(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        synopsis = in.readString();
        firstAiring = in.readString();
        vote = in.readDouble();
    }

    public static final Creator<AiringToday> CREATOR = new Creator<AiringToday>() {
        @Override
        public AiringToday createFromParcel(Parcel in) {
            return new AiringToday(in);
        }

        @Override
        public AiringToday[] newArray(int size) {
            return new AiringToday[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
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
