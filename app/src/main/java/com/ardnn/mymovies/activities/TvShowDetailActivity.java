package com.ardnn.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.adapters.CastAdapter;
import com.ardnn.mymovies.adapters.GenreAdapter;
import com.ardnn.mymovies.models.Cast;
import com.ardnn.mymovies.models.CastResponse;
import com.ardnn.mymovies.models.Genre;
import com.ardnn.mymovies.models.ImageSize;
import com.ardnn.mymovies.models.TvShow;
import com.ardnn.mymovies.utils.Const;
import com.ardnn.mymovies.networks.TvShowApiClient;
import com.ardnn.mymovies.networks.TvShowApiInterface;
import com.ardnn.mymovies.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    // extras
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_GENRES = "extra_genres";

    // tv show
    private TvShow tvShow;
    private TvShowApiInterface tvShowApiInterface;
    private int tvId;

    // genre
    private RecyclerView rvGenre;
    private GenreAdapter genreAdapter;
    private List<String> genreList;

    // cast
    private RecyclerView rvCast;
    private CastAdapter castAdapter;
    private List<Cast> castList;

    // widgets
    private Toolbar toolbarDetail;
    private ProgressBar pbDetail;
    private ImageView ivPoster, ivWallpaper, ivFavorite;
    private TextView tvTitle, tvEpisodes, tvSeasons, tvDuration,
            tvRating, tvFirstAir, tvLastAir, tvSynopsis, tvMore;
    private ConstraintLayout clWrapperSynopsis;

    // attributes
    private boolean isSynopsisCollapsed = true;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        // initialization
        initialization();

        // load tvShow data
        loadTvShowData();
        loadTvShowCast();

        // if  widgets clicked
        ivFavorite.setOnClickListener(this);
        clWrapperSynopsis.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_favorite_tv_show_detail:
                if (!isFavorite) {
                    ivFavorite.setImageResource(R.drawable.ic_favorite_true);
                    Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show();
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_favorite_false);
                    Toast.makeText(this, "Unfavorited", Toast.LENGTH_SHORT).show();
                }
                isFavorite = !isFavorite;

                break;
            case R.id.cl_wrapper_synopsis_tv_show_detail:
                if (isSynopsisCollapsed) {
                    tvSynopsis.setMaxLines(Integer.MAX_VALUE);
                } else {
                    tvSynopsis.setMaxLines(2);
                }
                isSynopsisCollapsed = !isSynopsisCollapsed;

                break;
        }
    }

    private void initialization() {
        tvShowApiInterface = TvShowApiClient.getRetrofit()
                .create(TvShowApiInterface.class);
        tvId = getIntent().getIntExtra(EXTRA_ID, 0);

        toolbarDetail = findViewById(R.id.toolbar_tv_show_detail);
        setSupportActionBar(toolbarDetail);

        rvGenre = findViewById(R.id.rv_genre_tv_show_detail);
        rvGenre.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false)
        );

        rvCast = findViewById(R.id.rv_cast_tv_show_detail);
        rvCast.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false)
        );

        pbDetail  = findViewById(R.id.pb_tv_show_detail);
        ivPoster = findViewById(R.id.iv_poster_tv_show_detail);
        ivWallpaper = findViewById(R.id.iv_wallpaper_tv_show_detail);
        ivFavorite = findViewById(R.id.iv_favorite_tv_show_detail);
        tvTitle = findViewById(R.id.tv_title_tv_show_detail);
        tvEpisodes = findViewById(R.id.tv_episodes_tv_show_detail);
        tvSeasons = findViewById(R.id.tv_seasons_tv_show_detail);
        tvDuration = findViewById(R.id.tv_duration_tv_show_detail);
        tvRating = findViewById(R.id.tv_rating_tv_show_detail);
        tvFirstAir = findViewById(R.id.tv_first_airing);
        tvLastAir = findViewById(R.id.tv_last_airing);
        tvSynopsis = findViewById(R.id.tv_synopsis_tv_show_detail);
        tvMore = findViewById(R.id.tv_more_tv_show_detail);
        clWrapperSynopsis = findViewById(R.id.cl_wrapper_synopsis_tv_show_detail);
    }

    private void loadTvShowData() {
        Call<TvShow> tvCall = tvShowApiInterface.getTvShow(tvId, Const.API_KEY);
        tvCall.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvShow = response.body();
                    setTvShowData();
                } else {
                    Toast.makeText(TvShowDetailActivity.this, "Response failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                Log.d("TV SHOW DETAIL", t.getLocalizedMessage());
                Toast.makeText(TvShowDetailActivity.this, "Response failure.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadTvShowCast() {
        Call<CastResponse> castResponseCall = tvShowApiInterface.getCast(tvId, Const.API_KEY);
        castResponseCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if (response.isSuccessful() && response.body().getCastList() != null) {
                    // put cast to list and set recyclerview cast
                    castList = response.body().getCastList();
                    castAdapter = new CastAdapter(castList);
                    rvCast.setAdapter(castAdapter);
                } else {
                    Toast.makeText(TvShowDetailActivity.this, "Response failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Log.d("TV SHOW DETAIL", t.getLocalizedMessage());
                Toast.makeText(TvShowDetailActivity.this, "Response failure.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTvShowData() {
        String posterUrl = tvShow.getPosterUrl(ImageSize.W342);
        String wallpaperUrl = tvShow.getWallpaperUrl(ImageSize.W780);
        String title = tvShow.getTitle();
        String duration = tvShow.getDurationList().get(0) + " mins";
        String firstAiring = Util.convertToDate(tvShow.getFirstAirDate());
        String lastAiring = tvShow.getLastAirDate() != null ?
                Util.convertToDate(tvShow.getLastAirDate()) : "Not yet known";
        String synopsis = tvShow.getSynopsis();
        String rating = tvShow.getRating() + "";
        String episodes = tvShow.getNumberOfEpisodes() + "";
        String seasons = tvShow.getNumberOfSeasons() + "";

        // get genres
        Map<Integer, String> genreMap = Genre.genreTvMap;
        List<Integer> genreIdList = getIntent().getIntegerArrayListExtra(EXTRA_GENRES);
        genreList = new ArrayList<>();
        for (Integer id : genreIdList) {
            genreList.add(genreMap.get(id));
        }

        // debug
        for (String genre : genreList) {
            Log.d("GENRE", genre);
        }

        // set recyclerview genre
        genreAdapter = new GenreAdapter(genreList);
        rvGenre.setAdapter(genreAdapter);

        // set to widgets
        tvTitle.setText(title);
        tvEpisodes.setText(episodes);
        tvSeasons.setText(seasons);
        tvDuration.setText(duration);
        tvRating.setText(rating);
        tvFirstAir.setText(firstAiring);
        tvLastAir.setText(lastAiring);
        tvSynopsis.setText(synopsis);
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