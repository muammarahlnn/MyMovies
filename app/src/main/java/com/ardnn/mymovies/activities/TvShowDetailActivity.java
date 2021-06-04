package com.ardnn.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.Genre;
import com.ardnn.mymovies.models.TvShow;
import com.ardnn.mymovies.networks.Const;
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

public class TvShowDetailActivity extends AppCompatActivity {

    // extras
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_GENRES = "extra_genres";

    // classes
    private TvShow tvShow;

    // widgets
    private ImageView ivPoster;
    private TextView tvTitle, tvSynopsis, tvRating, tvReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        // initialize widgets
        ivPoster = findViewById(R.id.iv_poster_tv_show_detail);
        tvTitle = findViewById(R.id.tv_title_tv_show_detail);
        tvSynopsis = findViewById(R.id.tv_synopsis_tv_show_detail);
        tvRating = findViewById(R.id.tv_rating_tv_show_detail);
        tvReleaseDate = findViewById(R.id.tv_release_date_tv_show_detail);

        // load tvShow detail data
        loadTvShowData();

    }

    private void loadTvShowData() {
        TvShowApiInterface tvShowApiInterface = TvShowApiClient.getRetrofit()
                .create(TvShowApiInterface.class);

        String tvId = String.valueOf(getIntent().getIntExtra(EXTRA_ID, 0));
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

    private void setTvShowData() {
        String title = tvShow.getTitle();
        String synopsis = tvShow.getSynopsis();
        String wallpaperUrl = tvShow.getWallpaperUrl();
        String releaseDate = Util.convertToDate(tvShow.getFirstAirDate());
        double rating = tvShow.getRating();

        // get genres
        Map<Integer, String> genreMap = Genre.genreMovieMap;
        List<Integer> genreIdList = getIntent().getIntegerArrayListExtra(EXTRA_GENRES);
        List<String> genreList = new ArrayList<>();
        for (Integer id : genreIdList) {
            genreList.add(genreMap.get(id));
        }

        // debug
        for (String genre : genreList) {
            Log.d("MOVIE DETAIL", genre);
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
        Util.changeActionBarTitle(this, title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // if button back on action bar is clicked
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}