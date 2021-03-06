package com.ardnn.mymovies.networks;

import com.ardnn.mymovies.models.CastResponse;
import com.ardnn.mymovies.models.Movie;
import com.ardnn.mymovies.models.NowPlayingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {
    @GET("now_playing")
    Call<NowPlayingResponse> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("{movie_id}/credits")
    Call<CastResponse> getCast(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

}
