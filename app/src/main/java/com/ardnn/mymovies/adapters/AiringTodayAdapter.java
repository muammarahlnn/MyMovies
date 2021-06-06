package com.ardnn.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.AiringToday;
import com.ardnn.mymovies.networks.Const;
import com.ardnn.mymovies.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class AiringTodayAdapter extends RecyclerView.Adapter<AiringTodayAdapter.ViewHolder> implements Filterable {
    private final List<AiringToday> airingTodayList;
    private List<AiringToday> airingTodayListFull;
    private final OnItemClick onItemClick;

    public AiringTodayAdapter(List<AiringToday> airingTodayList, OnItemClick onItemClick) {
        this.airingTodayList = airingTodayList;
        this.onItemClick = onItemClick;
        airingTodayListFull = new ArrayList<>(airingTodayList);
    }

    public void updateListFull(List<AiringToday> updatedList) {
        this.airingTodayListFull = new ArrayList<>(updatedList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_films, parent, false);

        return new AiringTodayAdapter.ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull AiringTodayAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return airingTodayList.size();
    }

    @Override
    public Filter getFilter() {
        return airingTodayFilter;
    }

    private final Filter airingTodayFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AiringToday> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(airingTodayListFull);
                Util.isSearching =  false;
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (AiringToday airingToday : airingTodayListFull) {
                    String title = airingToday.getTitle().toLowerCase();
                    String year = airingToday.getReleaseDate().substring(0, 4);
                    if (title.startsWith(filterPattern) || year.startsWith(filterPattern)) {
                        filteredList.add(airingToday);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            airingTodayList.clear();
            airingTodayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

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
                    .load(Const.IMG_URL_300 + airingTodayList.get(position).getPosterUrl())
                    .into(ivPoster);

            tvTitle.setText(airingTodayList.get(position).getTitle());
            tvRating.setText(String.valueOf(airingTodayList.get(position).getRating()));
            tvYear.setText(airingTodayList.get(position).getReleaseDate().substring(0, 4));
        }

    }

    public interface OnItemClick {
        void itemClicked(int position);
    }
}
