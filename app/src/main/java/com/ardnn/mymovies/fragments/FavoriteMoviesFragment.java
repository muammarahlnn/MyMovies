package com.ardnn.mymovies.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.activities.MovieDetailActivity;
import com.ardnn.mymovies.adapters.FavoritedMovieAdapter;
import com.ardnn.mymovies.adapters.OnItemClick;
import com.ardnn.mymovies.database.FavoritedDatabase;
import com.ardnn.mymovies.database.entities.FavoritedMovie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesFragment extends Fragment implements OnItemClick {

    // recyclerview favorited movies attr
    private FavoritedDatabase database;
    private RecyclerView rvMovies;
    private FavoritedMovieAdapter favoritedMovieAdapter;
    private List<FavoritedMovie> favoritedMovieList;

    // widgets
    private TextView clEmpty;

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
            // setup recyclerview favorited tv show
            favoritedMovieList = favoritedMovies;
            favoritedMovieAdapter = new FavoritedMovieAdapter(favoritedMovieList, FavoriteMoviesFragment.this);
            rvMovies.setAdapter(favoritedMovieAdapter);

            // check if there are favorited movies then remove alert and vice versa
            clEmpty.setVisibility(favoritedMovies.size() != 0 ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public void itemClicked(int position) {
        // go to movie detail
        Intent goToMovieDetail = new Intent(getActivity(), MovieDetailActivity.class);
        goToMovieDetail.putExtra(
                MovieDetailActivity.EXTRA_ID,
                favoritedMovieList.get(position).getId()
        );
        goToMovieDetail.putIntegerArrayListExtra(
                MovieDetailActivity.EXTRA_GENRES,
                (ArrayList<Integer>) genreToGenreList(favoritedMovieList.get(position).getGenres())
        );
        startActivity(goToMovieDetail);
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