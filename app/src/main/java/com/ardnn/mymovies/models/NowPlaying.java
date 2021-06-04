package com.ardnn.mymovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NowPlaying {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterUrl;

    @SerializedName("vote_average")
    private double rating;

    @SerializedName("genre_ids")
    private List<Integer> genreIdList;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public double getRating() {
        return rating;
    }

    public List<Integer> getGenreIdList() {
        return genreIdList;
    }
}
