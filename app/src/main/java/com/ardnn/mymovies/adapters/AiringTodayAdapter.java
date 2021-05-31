package com.ardnn.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.AiringToday;
import com.ardnn.mymovies.networks.Const;
import com.bumptech.glide.Glide;

import java.util.List;

public class AiringTodayAdapter extends RecyclerView.Adapter<AiringTodayAdapter.ViewHolder> {
    private List<AiringToday> airingTodayList;
    private OnItemClick onItemClick;

    public AiringTodayAdapter(List<AiringToday> airingTodayList, OnItemClick onItemClick) {
        this.airingTodayList = airingTodayList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_movie, parent, false);

        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull AiringTodayAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Const.IMG_URL_200 + airingTodayList.get(position).getImageUrl())
                .into(holder.ivPoster);

        holder.tvTitle.setText(airingTodayList.get(position).getName());
        holder.tvVote.setText(String.valueOf(airingTodayList.get(position).getVote()));
        holder.tvFirstAiring.setText(airingTodayList.get(position).getReleaseDate().substring(0, 4));
    }

    @Override
    public int getItemCount() {
        return airingTodayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClick onItemClick;
        ImageView ivPoster;
        TextView tvTitle, tvFirstAiring, tvVote;

        public ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);

            this.onItemClick = onItemClick;
            itemView.setOnClickListener(this);

            ivPoster = itemView.findViewById(R.id.iv_poster_item_movie);
            tvTitle = itemView.findViewById(R.id.tv_title_item_movie);
            tvVote = itemView.findViewById(R.id.tv_vote_item_movie);
            tvFirstAiring = itemView.findViewById(R.id.tv_year_released_item_movie);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onclick(getAdapterPosition());
        }
    }

    public interface OnItemClick {
        void onclick(int position);
    }
}
