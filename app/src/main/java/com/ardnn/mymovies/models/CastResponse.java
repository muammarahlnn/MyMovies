package com.ardnn.mymovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {
    @SerializedName("cast")
    @Expose
    private List<Cast> castList;

    public List<Cast> getCastList() {
        return castList;
    }
}
