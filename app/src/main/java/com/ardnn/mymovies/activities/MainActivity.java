package com.ardnn.mymovies.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.fragments.MoviesFragment;
import com.ardnn.mymovies.fragments.ProfileFragment;
import com.ardnn.mymovies.fragments.TvShowsFragment;
import com.ardnn.mymovies.utils.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    // widgets
    private BottomNavigationView bnvMain;

    // attributes
    private Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization
        bnvMain = findViewById(R.id.bnv_main);
        fragmentMap = new HashMap<>();

        // put all fragments to map
        fragmentMap.put(R.id.menu_item_movie, MoviesFragment.newInstance());
        fragmentMap.put(R.id.menu_item_tv_show, TvShowsFragment.newInstance());
        fragmentMap.put(R.id.menu_item_profile, ProfileFragment.newInstance());

        // set up BottomNavigationView
        bnvMain.setOnNavigationItemSelectedListener(this);
        bnvMain.setSelectedItemId(R.id.menu_item_movie);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = fragmentMap.get(item.getItemId());
        if (fragment != null) {
            switch (item.getItemId()) {
                case R.id.menu_item_movie:
                    Util.setActionBar(this,"Movies", R.drawable.ic_movie_yellow);
                    break;
                case R.id.menu_item_tv_show:
                    Util.setActionBar(this, "TV Shows", R.drawable.ic_tv_yellow);
                    break;
                case R.id.menu_item_profile:
                    Util.setActionBar(this, "Profile", R.drawable.ic_account_yellow);
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_main, fragment)
                    .commit();

            return true;
        }
        return false;
    }

}