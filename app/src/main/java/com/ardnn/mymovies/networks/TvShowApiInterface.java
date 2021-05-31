package com.ardnn.mymovies.networks;

import com.ardnn.mymovies.models.AiringTodayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowApiInterface {
    @GET("airing_today")
    Call<AiringTodayResponse> getAiringToday(
            @Query("api_key") String apiKey
    );
}
