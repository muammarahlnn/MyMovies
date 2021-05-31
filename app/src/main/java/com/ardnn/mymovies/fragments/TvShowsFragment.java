package com.ardnn.mymovies.fragments;

import android.content.Intent;
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
import com.ardnn.mymovies.activities.DetailActivity;
import com.ardnn.mymovies.activities.MainActivity;
import com.ardnn.mymovies.adapters.TvShowAdapter;
import com.ardnn.mymovies.models.TvShow;
import com.ardnn.mymovies.models.TvShowResponse;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.networks.TvShowApiClient;
import com.ardnn.mymovies.networks.TvShowApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsFragment extends Fragment implements TvShowAdapter.OnItemClick {

    // widgets
    private ProgressBar pbTvShows;

    // recyclerview attr
    private RecyclerView rvTvShows;
    private TvShowAdapter tvShowAdapter;
    private List<TvShow> tvShowList;

    public static TvShowsFragment newInstance() {
        TvShowsFragment fragment = new TvShowsFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.EXTRA_STRING, "TV Shows");
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_shows, container, false);

        // initialize widgets
        pbTvShows = view.findViewById(R.id.pb_tv_shows);

        // set recyclerview
        rvTvShows = view.findViewById(R.id.rv_tv_shows);
        rvTvShows.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadData();
        return view;
    }

    private void loadData() {
        TvShowApiInterface tvShowApiInterface = TvShowApiClient.getRetrofit()
                .create(TvShowApiInterface.class);

        Call<TvShowResponse> airingTodayResponseCall = tvShowApiInterface.getAiringToday(Const.API_KEY);
        airingTodayResponseCall.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful() && response.body().getAiringTodayList() != null) {
                    // put NowPlaying's data to list
                    tvShowList = response.body().getAiringTodayList();

                    // set recyclerview adapter
                    tvShowAdapter = new TvShowAdapter(tvShowList, TvShowsFragment.this);
                    rvTvShows.setAdapter(tvShowAdapter);
                } else {
                    Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
                }

                // remove progress bar
                pbTvShows.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Intent goToDetail = new Intent(getActivity(), DetailActivity.class);

        // put airing today's objects to intent
        goToDetail.putExtra(DetailActivity.EXTRA_MOVIE, tvShowList.get(position));
        startActivity(goToDetail);
    }
}