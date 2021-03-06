package com.ardnn.mymovies.networks;

import com.ardnn.mymovies.models.AiringTodayResponse;
import com.ardnn.mymovies.models.CastResponse;
import com.ardnn.mymovies.models.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowApiInterface {
    @GET("airing_today")
    Call<AiringTodayResponse> getAiringToday(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("{tv_id}")
    Call<TvShow> getTvShow(
            @Path("tv_id") int tvId,
            @Query("api_key") String apiKey
    );

    @GET("{tv_id}/credits")
    Call<CastResponse> getCast(
            @Path("tv_id") int tvId,
            @Query("api_key") String apiKey
    );
}
