package com.ardnn.mymovies.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.adapters.CastAdapter;
import com.ardnn.mymovies.adapters.GenreAdapter;
import com.ardnn.mymovies.models.Cast;
import com.ardnn.mymovies.models.CastResponse;
import com.ardnn.mymovies.models.Genre;
import com.ardnn.mymovies.models.ImageSize;
import com.ardnn.mymovies.models.Movie;
import com.ardnn.mymovies.utils.Const;
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

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    // extras
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_GENRES = "extra_genres";

    // movies
    private Movie movie;
    private MovieApiInterface movieApiInterface;
    private int movieId;

    // cast
    private RecyclerView rvCast;
    private CastAdapter castAdapter;
    private List<Cast> castList;

    // genre
    private RecyclerView rvGenre;
    private GenreAdapter genreAdapter;
    private List<String> genreList;

    // widgets
    private Toolbar toolbarDetail;
    private ProgressBar pbDetail;
    private ImageView ivPoster, ivWallpaper, ivFavorite;
    private TextView tvTitle, tvReleaseDate, tvDuration, tvRating, tvSynopsis, tvMore;
    private ConstraintLayout clWrapperSynopsis;

    // attributes
    private boolean isSynopsisCollapsed = true;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // initialization
        initialization();

        // load movie data
        loadMovieData();
        loadMovieCast();

        // if widget clicked
        ivFavorite.setOnClickListener(this);
        clWrapperSynopsis.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_favorite_detail:
                if (!isFavorite) {
                    ivFavorite.setImageResource(R.drawable.ic_favorite_true);
                    Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show();
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_favorite_false);
                    Toast.makeText(this, "Unfavorited", Toast.LENGTH_SHORT).show();
                }
                isFavorite = !isFavorite;
                break;
            case R.id.cl_wrapper_synopsis_movie_detail:
                if (isSynopsisCollapsed) {
                    tvSynopsis.setMaxLines(Integer.MAX_VALUE);
                    tvMore.setText("less");
                } else {
                    tvSynopsis.setMaxLines(2);
                    tvMore.setText("more");
                }
                isSynopsisCollapsed = !isSynopsisCollapsed;
                break;
        }
    }

    private void initialization() {
        movieApiInterface = MovieApiClient.getRetrofit()
                .create(MovieApiInterface.class);
        movieId = getIntent().getIntExtra(EXTRA_ID, 0);

        toolbarDetail = findViewById(R.id.toolbar_movie_detail);
        setSupportActionBar(toolbarDetail);

        rvGenre = findViewById(R.id.rv_genre_movie_detail);
        rvGenre.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false)
        );

        rvCast = findViewById(R.id.rv_cast_movie_detail);
        rvCast.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false)
        );

        pbDetail = findViewById(R.id.pb_movie_detail);
        ivPoster = findViewById(R.id.iv_poster_movie_detail);
        ivWallpaper = findViewById(R.id.iv_wallpaper_movie_detail);
        ivFavorite = findViewById(R.id.iv_favorite_detail);
        tvTitle = findViewById(R.id.tv_title_movie_detail);
        tvReleaseDate = findViewById(R.id.tv_release_date_movie_detail);
        tvDuration = findViewById(R.id.tv_duration_movie_detail);
        tvSynopsis = findViewById(R.id.tv_synopsis_movie_detail);
        tvRating = findViewById(R.id.tv_rating_movie_detail);
        tvMore = findViewById(R.id.tv_more_movie_detail);
        clWrapperSynopsis = findViewById(R.id.cl_wrapper_synopsis_movie_detail);
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
                    // put cast to list and set recyclerview cast
                    castList = response.body().getCastList();
                    castAdapter = new CastAdapter(castList);
                    rvCast.setAdapter(castAdapter);
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
        String posterUrl = movie.getPosterUrl(ImageSize.W342);
        String wallpaperUrl = movie.getWallpaperUrl(ImageSize.W780);
        String title = movie.getTitle();
        String releaseDate = Util.convertToDate(movie.getReleaseDate());
        String duration = movie.getDuration() + " mins";
        double rating = movie.getRating();
        String synopsis = movie.getSynopsis();

        // get genres
        Map<Integer, String> genreMap = Genre.genreMovieMap;
        List<Integer> genreIdList = getIntent().getIntegerArrayListExtra(EXTRA_GENRES);
        genreList = new ArrayList<>();
        for (Integer id : genreIdList) {
            genreList.add(genreMap.get(id));
        }

        // set recyclerview genre adapter
        genreAdapter = new GenreAdapter(genreList);
        rvGenre.setAdapter(genreAdapter);

        // set to widgets
        tvTitle.setText(title);
        tvSynopsis.setText(synopsis);
        tvReleaseDate.setText(releaseDate);
        tvDuration.setText(duration);
        tvRating.setText(String.valueOf(rating));
        Glide.with(this).load(posterUrl).into(ivPoster);
        Glide.with(this).load(wallpaperUrl).into(ivWallpaper);

        // remove progress bar
        pbDetail.setVisibility(View.GONE);

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