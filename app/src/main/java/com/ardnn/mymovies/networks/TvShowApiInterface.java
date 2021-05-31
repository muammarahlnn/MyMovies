package com.ardnn.mymovies.networks;

import com.ardnn.mymovies.models.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowApiInterface {
    @GET("airing_today")
    Call<TvShowResponse> getAiringToday(
            @Query("api_key") String apiKey
    );
}
