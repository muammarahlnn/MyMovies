package com.ardnn.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.Cast;
import com.ardnn.mymovies.models.CastResponse;
import com.ardnn.mymovies.models.Genre;
import com.ardnn.mymovies.models.Movie;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.networks.MovieApiClient;
import com.ardnn.mymovies.networks.MovieApiInterface;
import com.ardnn.mymovies.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    // extras
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_GENRES = "extra_genres";

    // classes
    private Movie movie;

    // widgets
    private Toolbar toolbarDetail;
    private ImageView ivPoster;
    private TextView tvTitle, tvSynopsis, tvRating, tvReleaseDate;

    // attributes
    private MovieApiInterface movieApiInterface;
    private int movieId;
    private List<Cast> castList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // initialization
        movieApiInterface = MovieApiClient.getRetrofit()
                .create(MovieApiInterface.class);
        movieId = getIntent().getIntExtra(EXTRA_ID, 0);

        toolbarDetail = findViewById(R.id.toolbar_movie_detail);
        setSupportActionBar(toolbarDetail);

        ivPoster = findViewById(R.id.iv_poster_movie_detail);
        tvTitle = findViewById(R.id.tv_title_movie_detail);
        tvSynopsis = findViewById(R.id.tv_synopsis_movie_detail);
        tvRating = findViewById(R.id.tv_rating_movie_detail);
        tvReleaseDate = findViewById(R.id.tv_release_date_movie_detail);

        // load movie data
        loadMovieData();
        loadMovieCast();
    }

    private void loadMovieData() {
        Call<Movie> movieCall = movieApiInterface.getMovie(movieId, Const.API_KEY);
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movie = response.body();
                    setMovieData();
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Response failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("MOVIE DETAIL", t.getLocalizedMessage());
                Toast.makeText(MovieDetailActivity.this, "Response failure.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadMovieCast() {
        Call<CastResponse> castResponseCall = movieApiInterface.getCast(movieId, Const.API_KEY);
        castResponseCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if (response.isSuccessful() && response.body().getCastList() != null) {
                    castList = response.body().getCastList();
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Response failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Log.d("MOVIE DETAIL", t.getLocalizedMessage());
                Toast.makeText(MovieDetailActivity.this, "Response failure.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMovieData() {
        String title = movie.getTitle();
        String synopsis = movie.getSynopsis();
        String wallpaperUrl = movie.getWallpaperUrl();
        String releaseDate = Util.convertToDate(movie.getReleaseDate());
        double rating = movie.getRating();

        // get genres
        Map<Integer, String> genreMap = Genre.genreMovieMap;
        List<Integer> genreIdList = getIntent().getIntegerArrayListExtra(EXTRA_GENRES);
        List<String> genreList = new ArrayList<>();
        for (Integer id : genreIdList) {
            genreList.add(genreMap.get(id));
        }

        // set to widgets
        tvTitle.setText(title);
        tvSynopsis.setText(synopsis);
        tvReleaseDate.setText(releaseDate);
        tvRating.setText(String.valueOf(rating));
        Glide.with(this)
                .load(Const.IMG_URL_500 + wallpaperUrl)
                .into(ivPoster);

        // change title action bar
        assert getSupportActionBar() != null : "Tenai action bar na";
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // if button back on action bar is clicked
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}