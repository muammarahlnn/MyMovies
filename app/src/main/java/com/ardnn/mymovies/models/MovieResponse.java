package com.ardnn.mymovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    @Expose
    private List<Movie> movieList;

    public List<Movie> getNowPlayings() {
        return movieList;
    }
}
