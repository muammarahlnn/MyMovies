package com.ardnn.mymovies.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ardnn.mymovies.database.entities.FavoritedMovie;
import com.ardnn.mymovies.database.entities.FavoritedTvShow;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface FavoritedDao {
    // == favorited movies ===================================
    @Query("SELECT * FROM favorited_movies")
    LiveData<List<FavoritedMovie>> getAllMovies();

    @Query("SELECT * FROM favorited_movies WHERE id=:id LIMIT 1")
    FavoritedMovie getMovie(int id);

    @Query("SELECT EXISTS (SELECT * FROM favorited_movies WHERE id = :id)")
    boolean isMovieExist(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMovie(FavoritedMovie movie);

    @Delete
    Completable deleteMovie(FavoritedMovie movie);

    // == favorited tv shows ===================================
    @Query("SELECT * FROM favorited_tv_shows")
    LiveData<List<FavoritedTvShow>> getAllTvShows();

    @Query("SELECT * FROM favorited_tv_shows WHERE id=:id LIMIT 1")
    FavoritedTvShow getTvShow(int id);

    @Query("SELECT EXISTS (SELECT * FROM favorited_tv_shows WHERE id = :id)")
    boolean isTvShowExist(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertTvShow(FavoritedTvShow tvShow);

    @Delete
    Completable deleteTvShow(FavoritedTvShow tvShow);


}
