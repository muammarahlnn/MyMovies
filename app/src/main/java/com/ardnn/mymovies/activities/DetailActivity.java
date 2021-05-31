package com.ardnn.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.Movie;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.utils.Util;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    // extras
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_SYNOPSIS = "extra_synopsis";
    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    public static final String EXTRA_RELEASE_DATE = "extra_release_date";
    public static final String EXTRA_VOTE = "extra_vote";
    public static final String EXTRA_MOVIE = "extra_movie";

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
        // get data from previous intent
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String title = movie.getName();
        String synopsis = movie.getSynopsis();
        String imageUrl = movie.getImageUrl();
        String releaseDate = Util.convertToDate(movie.getReleaseDate());
        double vote = movie.getVote();

        // set to widgets
        tvTitle.setText(title);
        tvSynopsis.setText(synopsis);
        tvReleaseDate.setText(releaseDate);
        tvVote.setText(String.valueOf(vote));
        Glide.with(this)
                .load(Const.IMG_URL_500 + imageUrl)
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