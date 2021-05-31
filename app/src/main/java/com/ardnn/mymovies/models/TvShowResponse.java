package com.ardnn.mymovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {
    @SerializedName("results")
    @Expose
    private List<TvShow> tvShowList;

    public List<TvShow> getAiringTodayList() {
        return tvShowList;
    }
}
