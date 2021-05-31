package com.ardnn.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.Film;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.utils.Util;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    // extras
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
        Film film = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String title = film.getName();
        String synopsis = film.getSynopsis();
        String imageUrl = film.getImageUrl();
        String releaseDate = Util.convertToDate(film.getReleaseDate());
        double vote = film.getVote();

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