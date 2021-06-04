package com.ardnn.mymovies.networks;

import com.ardnn.mymovies.models.AiringTodayResponse;
import com.ardnn.mymovies.models.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowApiInterface {
    @GET("airing_today")
    Call<AiringTodayResponse> getAiringToday(
            @Query("api_key") String apiKey
    );

    @GET()
    Call<TvShow> getTvShow(
            @Path("tv_id") String tvId,
            @Query("api_key") String apiKey
    );
}
