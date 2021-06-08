package com.ardnn.mymovies.models;


import com.ardnn.mymovies.utils.Const;
import com.google.gson.annotations.SerializedName;

public class Movie  {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String synopsis;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("runtime")
    private int duration;

    @SerializedName("vote_average")
    private double rating;

    @SerializedName("poster_path")
    private String posterUrl;

    @SerializedName("backdrop_path")
    private String wallpaperUrl;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public double getRating() {
        return rating;
    }

    public String getPosterUrl(ImageSize size) {
        return Const.IMG_URL + size.getValue() + posterUrl;
    }

    public String getWallpaperUrl(ImageSize size) {
        return Const.IMG_URL + size.getValue() + wallpaperUrl;
    }
}
