package com.ardnn.mymovies.models;

import com.ardnn.mymovies.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AiringToday {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String title;

    @SerializedName("first_air_date")
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

    public String getPosterUrl(ImageSize size) {
        return Const.IMG_URL + size.getValue() + posterUrl;
    }

    public double getRating() {
        return rating;
    }

    public List<Integer> getGenreIdList() {
        return genreIdList;
    }
}
