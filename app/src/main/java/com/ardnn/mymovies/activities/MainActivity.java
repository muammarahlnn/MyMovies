package com.ardnn.mymovies.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.fragments.MoviesFragment;
import com.ardnn.mymovies.fragments.FavoriteRootFragment;
import com.ardnn.mymovies.fragments.TvShowsFragment;
import com.ardnn.mymovies.utils.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    // widgets
    private BottomNavigationView bnvMain;

    // fragments
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final Fragment fragmentMovies = new MoviesFragment();
    private final Fragment fragmentTvShows = new TvShowsFragment();
    private final Fragment fragmentFavorite = new FavoriteRootFragment();
    private Fragment fragmentActive = fragmentMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization
        Util.initializeActionBar(this);
        bnvMain = findViewById(R.id.bnv_main);

        // add fragments to fragment manager
        addFragments();

        // if BottomNavigationView clicked
        bnvMain.setOnNavigationItemSelectedListener(this);

        // check if soft keyboard is showing or not
        final View viewRoot = findViewById(R.id.activity_root);
        viewRoot.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            viewRoot.getWindowVisibleDisplayFrame(rect);

            int heightDiff = viewRoot.getRootView().getHeight() - rect.height();
            if (heightDiff > 0.25 * viewRoot.getRootView().getHeight()) {
                bnvMain.setVisibility(View.GONE);
            } else {
                bnvMain.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addFragments() {
        // add main fragments and hide other fragments
        Util.setActionBar(this,"Movies", R.drawable.ic_movie_yellow);
        fragmentManager.beginTransaction()
                .add(R.id.fl_main, fragmentMovies)
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.fl_main, fragmentTvShows)
                .hide(fragmentTvShows)
                .commit();
        fragmentManager.beginTransaction()
                .add(R.id.fl_main, fragmentFavorite)
                .hide(fragmentFavorite)
                .commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment;
        switch (item.getItemId()) {
            case R.id.menu_item_movie:
                Util.setActionBar(this,"Movies", R.drawable.ic_movie_yellow);
                selectedFragment = fragmentMovies;
                break;
            case R.id.menu_item_tv_show:
                Util.setActionBar(this, "TV Shows", R.drawable.ic_tv_yellow);
                selectedFragment = fragmentTvShows;
                break;
            case R.id.menu_item_favorite:
                Util.setActionBar(this, "Favorite", R.drawable.ic_favorite_false);
                selectedFragment = fragmentFavorite;
                break;
            default:
                selectedFragment = null;
        }
        if (selectedFragment != null) {
            fragmentManager.beginTransaction()
                    .hide(fragmentActive)
                    .show(selectedFragment)
                    .commit();
            fragmentActive = selectedFragment;
            return true;
        }
        return false;
    }

}