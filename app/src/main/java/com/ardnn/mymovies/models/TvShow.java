package com.ardnn.mymovies.models;

import com.ardnn.mymovies.utils.Const;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TvShow {
    @SerializedName("backdrop_path")
    private String wallpaperUrl;

    @SerializedName("episode_run_time")
    private List<Integer> durationList;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("id")
    private int id;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("name")
    private String title;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("overview")
    private String synopsis;

    @SerializedName("poster_path")
    private String posterUrl;

    @SerializedName("vote_average")
    private double rating;

    public String getWallpaperUrl(ImageSize size) {
        return Const.IMG_URL + size.getValue() + wallpaperUrl;
    }

    public List<Integer> getDurationList() {
        return durationList;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public int getId() {
        return id;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPosterUrl(ImageSize size) {
        return Const.IMG_URL + size.getValue() + posterUrl;
    }

    public double getRating() {
        return rating;
    }
}
