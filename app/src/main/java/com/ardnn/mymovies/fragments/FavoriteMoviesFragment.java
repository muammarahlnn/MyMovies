package com.ardnn.mymovies.fragments;

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
import com.ardnn.mymovies.adapters.FavoritedMovieAdapter;
import com.ardnn.mymovies.database.FavoritedDatabase;
import com.ardnn.mymovies.database.entities.FavoritedMovie;

import java.util.List;

public class FavoriteMoviesFragment extends Fragment implements FavoritedMovieAdapter.OnItemClick {

    // recyclerview favorited movies attr
    private FavoritedDatabase database;
    private RecyclerView rvMovies;
    private FavoritedMovieAdapter favoritedMovieAdapter;
    private List<FavoritedMovie> favoritedMovieList;

    // widgets
    private ConstraintLayout clEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);

        // initialization
        database = FavoritedDatabase.getDatabase(getActivity().getApplicationContext());
        clEmpty = view.findViewById(R.id.cl_empty_favorite_movies);
        rvMovies = view.findViewById(R.id.rv_favorite_movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadFavoritedMoviesData();

        return view;
    }

    private void loadFavoritedMoviesData() {
        database.favoritedDao().getAllMovies().observe(getActivity(), favoritedMovies -> {
            if (favoritedMovies.size() != 0) {
                // set up recyclerview favorited movies
                favoritedMovieList = favoritedMovies;
                favoritedMovieAdapter = new FavoritedMovieAdapter(favoritedMovieList, FavoriteMoviesFragment.this);
                rvMovies.setAdapter(favoritedMovieAdapter);

                // remove alert
                clEmpty.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void itemCLicked(int position) {

    }
}