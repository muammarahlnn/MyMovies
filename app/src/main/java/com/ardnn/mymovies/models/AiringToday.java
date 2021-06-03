package com.ardnn.mymovies.models;

import com.google.gson.annotations.SerializedName;

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
}
