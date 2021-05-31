package com.ardnn.mymovies.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.activities.DetailActivity;
import com.ardnn.mymovies.activities.MainActivity;
import com.ardnn.mymovies.adapters.NowPlayingAdapter;
import com.ardnn.mymovies.models.NowPlaying;
import com.ardnn.mymovies.models.NowPlayingResponse;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.networks.MovieApiClient;
import com.ardnn.mymovies.networks.MovieApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment implements NowPlayingAdapter.OnItemClick {
    // classes
    private NowPlayingAdapter nowPlayingAdapter;

    // widgets
    private RecyclerView rvMovies;

    // attributes
    private List<NowPlaying> nowPlayings;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.EXTRA_STRING, "Movies");
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movie, container, false);

        // initialize widgets
        rvMovies = view.findViewById(R.id.rv_movies);

        // set recyclerview layout
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        loadData();
        return view;
    }

    private void loadData() {
        MovieApiInterface movieApiInterface = MovieApiClient.getRetrofit()
                .create(MovieApiInterface.class);

        Call<NowPlayingResponse> nowPlayingResponseCall = movieApiInterface.getNowPlaying(Const.API_KEY);
        nowPlayingResponseCall.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                if (response.isSuccessful() && response.body().getNowPlayings() != null) {
                    // set the now playing's data to list and put it to recyclerview
                    nowPlayings = response.body().getNowPlayings();
                    nowPlayingAdapter = new NowPlayingAdapter(nowPlayings, MovieFragment.this);
                    rvMovies.setAdapter(nowPlayingAdapter);
                } else {
                    Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Response Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Intent goToDetail = new Intent(getActivity(), DetailActivity.class);

        // put now playing' object to intent
        goToDetail.putExtra(DetailActivity.EXTRA_MOVIE, nowPlayings.get(position));
//        goToDetail.putExtra(DetailActivity.EXTRA_TITLE, nowPlayings.get(position).getTitle());
//        goToDetail.putExtra(DetailActivity.EXTRA_SYNOPSIS, nowPlayings.get(position).getSynopsis());
//        goToDetail.putExtra(DetailActivity.EXTRA_IMAGE_URL, nowPlayings.get(position).getImageUrl());
//        goToDetail.putExtra(DetailActivity.EXTRA_RELEASE_DATE, nowPlayings.get(position).getReleaseDate());
//        goToDetail.putExtra(DetailActivity.EXTRA_VOTE, nowPlayings.get(position).getVote());

        startActivity(goToDetail);

    }
}