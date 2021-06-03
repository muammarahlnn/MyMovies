package com.ardnn.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.NowPlaying;
import com.ardnn.mymovies.networks.Const;
import com.bumptech.glide.Glide;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {
    private final List<NowPlaying> nowPlayingList;
    private final OnItemClick onItemClick;

    public NowPlayingAdapter(List<NowPlaying> nowPlayingList, OnItemClick onItemClick) {
        this.nowPlayingList = nowPlayingList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_films, parent, false);

        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return nowPlayingList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvYear, tvRating;
        private final ImageView ivPoster;

        public ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            itemView.setOnClickListener(v -> onItemClick.itemClicked(getAbsoluteAdapterPosition()));

            // initialize widgets
            tvTitle = itemView.findViewById(R.id.tv_title_item_film);
            tvYear = itemView.findViewById(R.id.tv_year_item_film);
            tvRating = itemView.findViewById(R.id.tv_rating_item_film);
            ivPoster = itemView.findViewById(R.id.iv_poster_item_film);
        }

        private void onBind(int position) {
            // set data to widgets
            Glide.with(itemView.getContext())
                    .load(Const.IMG_URL_300 + nowPlayingList.get(position).getPosterUrl())
                    .into(ivPoster);

            tvTitle.setText(nowPlayingList.get(position).getTitle());
            tvRating.setText(String.valueOf(nowPlayingList.get(position).getRating()));
            tvYear.setText(nowPlayingList.get(position).getReleaseDate().substring(0, 4));
        }
    }

    public interface OnItemClick {
        void itemClicked(int position);
    }
}
