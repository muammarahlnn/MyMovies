package com.ardnn.mymovies.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.adapters.FavoriteRootAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavoriteRootFragment extends Fragment {

    // viewpager attr
    private FavoriteRootAdapter favoriteRootAdapter;
    private TabLayout tlFavorite;
    private ViewPager2 vp2Favorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorite_root, container, false);

        // initialize widgets
        tlFavorite = view.findViewById(R.id.tl_favorite_root);
        vp2Favorite = view.findViewById(R.id.vp2_favorite_root);

        // set favorite root adapter
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        favoriteRootAdapter = new FavoriteRootAdapter(fragmentManager, getLifecycle());
        vp2Favorite.setAdapter(favoriteRootAdapter);

        // add tablayout's tab
        tlFavorite.addTab(tlFavorite.newTab().setText("Movies"));
        tlFavorite.addTab(tlFavorite.newTab().setText("TV Shows"));
        tlFavorite.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null) vp2Favorite.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        });

        // if viewpager slided
        vp2Favorite.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tlFavorite.selectTab(tlFavorite.getTabAt(position));
            }
        });

        return view;
    }
}