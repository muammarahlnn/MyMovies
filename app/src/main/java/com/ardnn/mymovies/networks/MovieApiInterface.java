package com.ardnn.mymovies.networks;

import com.ardnn.mymovies.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiInterface {
    @GET("now_playing")
    Call<MovieResponse> getNowPlaying(
            @Query("api_key") String apiKey
    );
}
