package com.ardnn.mymovies.fragments;

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
import com.ardnn.mymovies.adapters.AiringTodayAdapter;
import com.ardnn.mymovies.models.Genre;
import com.ardnn.mymovies.models.GenreResponse;
import com.ardnn.mymovies.models.AiringToday;
import com.ardnn.mymovies.models.AiringTodayResponse;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.networks.GenreApiClient;
import com.ardnn.mymovies.networks.GenreApiInterface;
import com.ardnn.mymovies.networks.AiringTodayApiClient;
import com.ardnn.mymovies.networks.AiringTodayApiInterface;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsFragment extends Fragment implements AiringTodayAdapter.OnItemClick {

    // widgets
    private ProgressBar pbTvShows;

    // recyclerview attr
    private RecyclerView rvAiringToday;
    private AiringTodayAdapter airingTodayAdapter;
    private List<AiringToday> airingTodayList;

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
        rvAiringToday = view.findViewById(R.id.rv_tv_shows);
        rvAiringToday.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadData();
        return view;
    }

    private void loadData() {
        AiringTodayApiInterface airingTodayApiInterface = AiringTodayApiClient.getRetrofit()
                .create(AiringTodayApiInterface.class);

        Call<AiringTodayResponse> airingTodayResponseCall = airingTodayApiInterface.getAiringToday(Const.API_KEY);
        airingTodayResponseCall.enqueue(new Callback<AiringTodayResponse>() {
            @Override
            public void onResponse(Call<AiringTodayResponse> call, Response<AiringTodayResponse> response) {
                if (response.isSuccessful() && response.body().getAiringTodayList() != null) {
                    // put NowPlaying's data to list
                    airingTodayList = response.body().getAiringTodayList();

                    // set recyclerview adapter
                    airingTodayAdapter = new AiringTodayAdapter(airingTodayList, TvShowsFragment.this);
                    rvAiringToday.setAdapter(airingTodayAdapter);
                } else {
                    Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
                }

                // remove progress bar
                pbTvShows.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<AiringTodayResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Response failed.", Toast.LENGTH_SHORT).show();
            }
        });

        // load genre tv data
        if (Genre.genreTvMap == null) {
            GenreApiInterface genreApiInterface = GenreApiClient.getRetrofit()
                    .create(GenreApiInterface.class);

            Call<GenreResponse> genreResponseCall = genreApiInterface.getGenreTv(Const.API_KEY);
            genreResponseCall.enqueue(new Callback<GenreResponse>() {
                @Override
                public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                    if (response.isSuccessful() && response.body().getGenreList() != null) {
                        // put genre tv to list
                        List<Genre> genreTvList = response.body().getGenreList();

                        // initialize genreTvMap and add key-value pair to it with genreTvList data
                        Genre.genreTvMap = new HashMap<>();
                        for (Genre genre : genreTvList) {
                            Genre.genreTvMap.put(genre.getId(), genre.getName());
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
        Toast.makeText(getActivity(), "You clicked " + airingTodayList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}