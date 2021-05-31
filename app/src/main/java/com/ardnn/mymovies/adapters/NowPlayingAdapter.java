package com.ardnn.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.Movie;
import com.ardnn.mymovies.networks.Const;
import com.bumptech.glide.Glide;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {
    private List<Movie> movieList;
    private OnItemClick onItemClick;

    public NowPlayingAdapter(List<Movie> movieList, OnItemClick onItemClick) {
        this.movieList = movieList;
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
        Glide.with(holder.itemView.getContext())
                .load(Const.IMG_URL_200 + movieList.get(position).getImageUrl())
                .into(holder.ivPoster);

        holder.tvTitle.setText(movieList.get(position).getName());
        holder.tvVote.setText(String.valueOf(movieList.get(position).getVote()));
        holder.tvYearReleased.setText(movieList.get(position).getReleaseDate().substring(0, 4));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClick onItemClick;
        ImageView ivPoster;
        TextView tvTitle, tvYearReleased, tvVote;

        ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);

            this.onItemClick = onItemClick;
            itemView.setOnClickListener(this);

            ivPoster = itemView.findViewById(R.id.iv_poster_item_movie);
            tvTitle = itemView.findViewById(R.id.tv_title_item_movie);
            tvVote = itemView.findViewById(R.id.tv_vote_item_movie);
            tvYearReleased = itemView.findViewById(R.id.tv_year_released_item_movie);

        }

        @Override
        public void onClick(View v) {
            onItemClick.onClick(getAdapterPosition());
        }
    }

    public interface OnItemClick {
        void onClick(int position);
    }
}
