package com.ardnn.mymovies.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ardnn.mymovies.models.ImageSize;
import com.ardnn.mymovies.utils.Const;

import java.io.Serializable;

@Entity(tableName = "favorited_tv_shows")
public class FavoritedTvShow implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "poster_url")
    private String posterUrl;

    public String getPosterUrl() {
        return posterUrl;
    }

    @ColumnInfo(name = "rating")
    private double rating;

    public FavoritedTvShow(int id, String title, String releaseDate, String posterUrl, double rating) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.rating = rating;
    }

    public FavoritedTvShow(int id) {
        this.id = id;
    }

    public FavoritedTvShow() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl(ImageSize size) {
        return Const.IMG_URL + size.getValue() + posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

