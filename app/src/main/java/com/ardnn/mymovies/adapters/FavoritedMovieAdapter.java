package com.ardnn.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.database.entities.FavoritedMovie;
import com.ardnn.mymovies.models.ImageSize;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavoritedMovieAdapter extends RecyclerView.Adapter<FavoritedMovieAdapter.ViewHolder> {
    private List<FavoritedMovie> favoritedMovieList;
    private OnItemClick onItemClick;

    public FavoritedMovieAdapter(List<FavoritedMovie> favoritedMovieList, OnItemClick onItemClick) {
        this.favoritedMovieList = favoritedMovieList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_favorited, parent, false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(favoritedMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return favoritedMovieList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvYear, tvRating;
        private final ImageView ivPoster;

        public ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            itemView.setOnClickListener(v -> onItemClick.itemCLicked(getAbsoluteAdapterPosition()));

            // initialize widgets
            tvTitle = itemView.findViewById(R.id.tv_title_item_favorited);
            tvYear = itemView.findViewById(R.id.tv_year_item_favorited);
            tvRating = itemView.findViewById(R.id.tv_rating_item_favorited);
            ivPoster = itemView.findViewById(R.id.iv_poster_item_favorited);
        }

        private void onBind(FavoritedMovie favoritedMovie) {
            // set data to widgets
            Glide.with(itemView.getContext())
                    .load(favoritedMovie.getPosterUrl(ImageSize.W200))
                    .into(ivPoster);
            tvTitle.setText(favoritedMovie.getTitle());
            tvYear.setText(favoritedMovie.getReleaseDate().substring(0, 4));
            tvRating.setText(String.valueOf(favoritedMovie.getRating()));
        }
    }

    public interface OnItemClick {
        void itemCLicked(int position);
    }
}
