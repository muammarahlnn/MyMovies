package com.ardnn.mymovies.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.activities.DetailActivity;
import com.ardnn.mymovies.activities.MainActivity;
import com.ardnn.mymovies.adapters.MovieAdapter;
import com.ardnn.mymovies.models.Genre;
import com.ardnn.mymovies.models.GenreResponse;
import com.ardnn.mymovies.models.Movie;
import com.ardnn.mymovies.models.MovieResponse;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.networks.GenreApiClient;
import com.ardnn.mymovies.networks.GenreApiInterface;
import com.ardnn.mymovies.networks.MovieApiClient;
import com.ardnn.mymovies.networks.MovieApiInterface;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment implements MovieAdapter.OnItemClick {

    // widgets
    private ProgressBar pbMovies;

    // recyclerview attr
    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.EXTRA_STRING, "Movies");
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
        rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadData();


        return view;
    }

    private void loadData() {
        // load movies data
        MovieApiInterface movieApiInterface = MovieApiClient.getRetrofit()
                .create(MovieApiInterface.class);

        Call<MovieResponse> nowPlayingResponseCall = movieApiInterface.getNowPlaying(Const.API_KEY);
        nowPlayingResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body().getNowPlayings() != null) {
                    // put NowPlaying's data to list
                    movieList = response.body().getNowPlayings();

                    // set recyclerview adapter
                    movieAdapter = new MovieAdapter(movieList, MoviesFragment.this);
                    rvMovies.setAdapter(movieAdapter);
                } else {
                    Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
                }

                // remove progress bar
                pbMovies.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
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
    public void onClick(int position) {
        Intent goToDetail = new Intent(getActivity(), DetailActivity.class);

        // put now playing' object to intent
        goToDetail.putExtra(DetailActivity.EXTRA_FILM, movieList.get(position));
        startActivity(goToDetail);
    }
}