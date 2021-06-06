package com.ardnn.mymovies.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.activities.MainActivity;
import com.ardnn.mymovies.activities.MovieDetailActivity;
import com.ardnn.mymovies.adapters.NowPlayingAdapter;
import com.ardnn.mymovies.models.Genre;
import com.ardnn.mymovies.models.GenreResponse;
import com.ardnn.mymovies.models.NowPlayingResponse;
import com.ardnn.mymovies.models.NowPlaying;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.networks.GenreApiClient;
import com.ardnn.mymovies.networks.GenreApiInterface;
import com.ardnn.mymovies.networks.MovieApiClient;
import com.ardnn.mymovies.networks.MovieApiInterface;
import com.ardnn.mymovies.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment implements NowPlayingAdapter.OnItemClick {

    // widgets
    private ProgressBar pbMovies;

    // recyclerview attr
    private RecyclerView rvNowPlaying;
    private NowPlayingAdapter nowPlayingAdapter;
    private List<NowPlaying> nowPlayingList;

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movies, container, false);
        setHasOptionsMenu(true);

        // initialize widgets
        pbMovies = view.findViewById(R.id.pb_movies);

        // set recyclerview
        rvNowPlaying = view.findViewById(R.id.rv_movies);
        rvNowPlaying.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // load data from TMDB API
        loadData();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_main_item, menu);
        super.onCreateOptionsMenu(menu, inflater);

        // if user is searching a movie
        MenuItem searchItem = menu.findItem(R.id.toolbar_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                nowPlayingAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void loadData() {
        // load movies data
        MovieApiInterface movieApiInterface = MovieApiClient.getRetrofit()
                .create(MovieApiInterface.class);

        Call<NowPlayingResponse> nowPlayingResponseCall = movieApiInterface.getNowPlaying(Const.API_KEY);
        nowPlayingResponseCall.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                if (response.isSuccessful() && response.body().getNowPlayingList() != null) {
                    // put NowPlaying's data to list
                    nowPlayingList = response.body().getNowPlayingList();

                    // set recyclerview adapter
                    nowPlayingAdapter = new NowPlayingAdapter(nowPlayingList, MoviesFragment.this);
                    rvNowPlaying.setAdapter(nowPlayingAdapter);
                } else {
                    Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
                }

                // remove progress bar
                pbMovies.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Response Failed.", Toast.LENGTH_SHORT).show();
            }
        });

        // load genre movies data
        if (Genre.genreMovieMap == null) {
            GenreApiInterface genreApiInterface = GenreApiClient.getRetrofit()
                    .create(GenreApiInterface.class);

            Call<GenreResponse> genreResponseCall = genreApiInterface.getGenreMovie(Const.API_KEY);
            genreResponseCall.enqueue(new Callback<GenreResponse>() {
                @Override
                public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                    if (response.isSuccessful() && response.body().getGenreList() != null) {
                        // put genre movies to list
                        List<Genre> genreMovieList = response.body().getGenreList();

                        //  initialize genreMovieMap and add key-value pair to it with genreMovieList data
                        Genre.genreMovieMap = new HashMap<>();
                        for (Genre genre : genreMovieList) {
                            Genre.genreMovieMap.put(genre.getId(), genre.getName());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<GenreResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public void itemClicked(int position) {
        // go to movie detail
        Intent goToMovieDetail = new Intent(getActivity(), MovieDetailActivity.class);
        goToMovieDetail.putExtra(
                MovieDetailActivity.EXTRA_ID,
                nowPlayingList.get(position).getId()
        );
        goToMovieDetail.putIntegerArrayListExtra(
                MovieDetailActivity.EXTRA_GENRES,
                (ArrayList<Integer>) nowPlayingList.get(position).getGenreIdList()
        );
        startActivity(goToMovieDetail);
    }
}