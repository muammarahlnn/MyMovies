package com.ardnn.mymovies.models;

import com.google.gson.annotations.SerializedName;

public abstract class Film {
    @SerializedName("poster_path")
    protected String posterUrl;

    @SerializedName("backdrop_path")
    protected String wallpaperUrl;

    @SerializedName("overview")
    protected String synopsis;

    @SerializedName("vote_average")
    protected double vote;

    // getter and setter ------------------------------------
    public abstract String getName();
    public abstract void setName(String name);

    public abstract String getReleaseDate();
    public abstract void setReleaseDate(String date);

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getWallpaperUrl() {
        return wallpaperUrl;
    }

    public void setWallpaperUrl(String wallpaperUrl) {
        this.wallpaperUrl = wallpaperUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }
}
