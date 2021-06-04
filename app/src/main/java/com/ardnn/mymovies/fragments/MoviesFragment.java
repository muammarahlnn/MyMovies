package com.ardnn.mymovies.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.EXTRA_TITLE, "Movies");
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movies, container, false);

        // initialize widgets
        pbMovies = view.findViewById(R.id.pb_movies);

        // set recyclerview
        rvNowPlaying = view.findViewById(R.id.rv_movies);
        rvNowPlaying.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadData();


        return view;
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