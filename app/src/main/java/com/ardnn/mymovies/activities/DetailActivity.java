package com.ardnn.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.Film;
import com.ardnn.mymovies.models.Genre;
import com.ardnn.mymovies.models.Movie;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    // extras
    public static final String EXTRA_FILM = "extra_film";

    // widgets
    ImageView ivPoster;
    TextView tvTitle, tvSynopsis, tvVote, tvReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // initialize widgets
        ivPoster = findViewById(R.id.iv_poster_detail);
        tvTitle = findViewById(R.id.tv_title_detail);
        tvSynopsis = findViewById(R.id.tv_synopsis_detail);
        tvVote = findViewById(R.id.tv_vote_detail);
        tvReleaseDate = findViewById(R.id.tv_release_date_detail);

        // set movie data to widgets
        setMovieData();
    }

    private void setMovieData() {
//        // get data from previous intent
//        Film film = getIntent().getParcelableExtra(EXTRA_FILM);
//        String title = film.getName();
//        String synopsis = film.getSynopsis();
//        String wallpaperUrl = film.getWallpaperUrl();
//        String releaseDate = Util.convertToDate(film.getReleaseDate());
//        double vote = film.getVote();
//
//        // get genres
//        Map<Integer, String> genreMap = film instanceof Movie ?
//                Genre.genreMovieMap : Genre.genreTvMap;
//        List<Integer> genreIdList = film.getGenreIdList();
//        List<String> genreList = new ArrayList<>();
//        for (Integer id : genreIdList) {
//            genreList.add(genreMap.get(id));
//        }
//
//        // set to widgets
//        tvTitle.setText(title);
//        tvSynopsis.setText(synopsis);
//        tvReleaseDate.setText(releaseDate);
//        tvVote.setText(String.valueOf(vote));
//        Glide.with(this)
//                .load(Const.IMG_URL_500 + wallpaperUrl)
//                .into(ivPoster);
//
//        // change title action bar
//        assert getSupportActionBar() != null : "Tenai action bar na";
//        Util.changeActionBarTitle(this, title);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // if button back on action bar is clicked
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}