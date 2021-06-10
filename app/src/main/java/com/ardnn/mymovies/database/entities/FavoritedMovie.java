package com.ardnn.mymovies.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ardnn.mymovies.models.ImageSize;
import com.ardnn.mymovies.utils.Const;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "favorited_movies")
public class FavoritedMovie implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "poster_url")
    private String posterUrl;

    @ColumnInfo(name = "rating")
    private double rating;

    @ColumnInfo(name = "genres")
    private String genres;

    public FavoritedMovie(int id, String title, String releaseDate, String posterUrl, String genres, double rating) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.genres = genres;
        this.rating = rating;
    }

    public FavoritedMovie(int id) {
        this.id = id;
    }

    public FavoritedMovie() {}

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

    public String getPosterUrl() {
        return posterUrl;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
