package com.ardnn.mymovies.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.activities.MovieDetailActivity;
import com.ardnn.mymovies.activities.TvShowDetailActivity;
import com.ardnn.mymovies.adapters.FavoritedTvShowAdapter;
import com.ardnn.mymovies.adapters.OnItemClick;
import com.ardnn.mymovies.database.FavoritedDatabase;
import com.ardnn.mymovies.database.entities.FavoritedTvShow;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTvShowsFragment extends Fragment implements OnItemClick {

    // recyclerview favorited tv shows attr
    private FavoritedDatabase database;
    private RecyclerView rvTvShows;
    private FavoritedTvShowAdapter favoritedTvShowAdapter;
    private List<FavoritedTvShow> favoritedTvShowList;

    // widgets
    private ConstraintLayout clEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv_shows, container, false);

        // initialization
        database = FavoritedDatabase.getDatabase(getActivity().getApplicationContext());
        clEmpty = view.findViewById(R.id.cl_empty_favorite_tv_shows);
        rvTvShows = view.findViewById(R.id.rv_favorite_tv_shows);
        rvTvShows.setLayoutManager(new LinearLayoutManager(getActivity()));

        // load data
        loadFavoritedTvShowsData();

        return view;
    }

    private void loadFavoritedTvShowsData() {
        database.favoritedDao().getAllTvShows().observe(getActivity(), favoritedTvShows -> {
            // setup recyclerview favorited tv shows
            favoritedTvShowList = favoritedTvShows;
            favoritedTvShowAdapter = new FavoritedTvShowAdapter(favoritedTvShowList, FavoriteTvShowsFragment.this);
            rvTvShows.setAdapter(favoritedTvShowAdapter);

            // check if there are favorited tv show then remove alert and vice versa
            clEmpty.setVisibility(favoritedTvShows.size() != 0 ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public void itemClicked(int position) {
        // go to movie detail
        Intent goToTvShowDetail = new Intent(getActivity(), TvShowDetailActivity.class);
        goToTvShowDetail.putExtra(
                TvShowDetailActivity.EXTRA_ID,
                favoritedTvShowList.get(position).getId()
        );
        goToTvShowDetail.putIntegerArrayListExtra(
                TvShowDetailActivity.EXTRA_GENRES,
                (ArrayList<Integer>) genreToGenreList(favoritedTvShowList.get(position).getGenres())
        );
        startActivity(goToTvShowDetail);
    }

    private List<Integer> genreToGenreList(String genres) {
        List<Integer> ans = new ArrayList<>();
        String[] temp = genres.split(";");
        for (String genre : temp) {
            ans.add(Integer.valueOf(genre));
        }
        return ans;
    }
}