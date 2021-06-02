package com.ardnn.mymovies.networks;

import com.ardnn.mymovies.models.GenreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenreApiInterface {
    @GET("movie/list")
    Call<GenreResponse> getGenreMovie(
            @Query("api_key")
            String apiKey
    );

    @GET("tv/list")
    Call<GenreResponse> getGenreTv(
            @Query("api_key")
            String apiKey
    );
}
