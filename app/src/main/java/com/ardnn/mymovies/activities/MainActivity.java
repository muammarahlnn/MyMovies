package com.ardnn.mymovies.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.fragments.MoviesFragment;
import com.ardnn.mymovies.fragments.ProfileFragment;
import com.ardnn.mymovies.fragments.TvShowsFragment;
import com.ardnn.mymovies.utils.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    // extras
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_ICON = "extra_icon";

    // widgets
    private Toolbar toolbarMain;
    private BottomNavigationView bnvMain;
    private ImageView ivIconToolbar;
    private TextView tvTitleToolbar;

    // attributes
    private Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization
        toolbarMain = findViewById(R.id.toolbar_main);
        ivIconToolbar = findViewById(R.id.iv_icon_toolbar);
        tvTitleToolbar = findViewById(R.id.tv_title_toolbar);

        bnvMain = findViewById(R.id.bnv_main);
        fragmentMap = new HashMap<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // put all fragments to map
        fragmentMap.put(R.id.menu_item_movie, MoviesFragment.newInstance());
        fragmentMap.put(R.id.menu_item_tv_show, TvShowsFragment.newInstance());
        fragmentMap.put(R.id.menu_item_profile, ProfileFragment.newInstance());

        bnvMain.setOnNavigationItemSelectedListener(this);
        bnvMain.setSelectedItemId(R.id.menu_item_movie);

        // change action bar's title color
//        Util.changeActionBarTitle(this, MoviesFragment.newInstance().getArguments().getString(EXTRA_TITLE));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = fragmentMap.get(item.getItemId());
        if (fragment != null) {
            assert fragment.getArguments() != null;

            // set icon
            switch (item.getItemId()) {
                case R.id.menu_item_movie:
                    tvTitleToolbar.setText("Movies");
                    ivIconToolbar.setBackgroundResource(R.drawable.ic_movie_yellow);
                    break;
                case R.id.menu_item_tv_show:
                    tvTitleToolbar.setText("TV Shows");
                    ivIconToolbar.setBackgroundResource(R.drawable.ic_tv_yellow);
                    break;

                case R.id.menu_item_profile:
                    tvTitleToolbar.setText("Profile");
                    ivIconToolbar.setBackgroundResource(R.drawable.ic_account_yellow);
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_main, fragment)
                    .commit();
        }
        return true;
    }
}