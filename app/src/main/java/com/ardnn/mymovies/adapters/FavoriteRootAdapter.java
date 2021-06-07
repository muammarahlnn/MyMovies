package com.ardnn.mymovies.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ardnn.mymovies.fragments.FavoriteMoviesFragment;
import com.ardnn.mymovies.fragments.FavoriteTvShowsFragment;

public class FavoriteRootAdapter extends FragmentStateAdapter {

    public FavoriteRootAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new FavoriteMoviesFragment() : new FavoriteTvShowsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
