package com.ardnn.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardnn.mymovies.R;
import com.ardnn.mymovies.models.Cast;
import com.ardnn.mymovies.models.ImageSize;
import com.bumptech.glide.Glide;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private final List<Cast> castList;

    public CastAdapter(List<Cast> castList) {
        this.castList = castList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_cast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.OnBind(castList.get(position));
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final TextView tvName, tvCharacter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize widgets
            ivImage = itemView.findViewById(R.id.iv_image_item_cast );
            tvName = itemView.findViewById(R.id.tv_name_item_cast);
            tvCharacter = itemView.findViewById(R.id.tv_character_item_cast);
        }

        private void OnBind(Cast cast) {
            Glide.with(itemView.getContext())
                    .load(cast.getImageUrl(ImageSize.W342))
                    .into(ivImage);
            tvName.setText(cast.getName());
            tvCharacter.setText(cast.getCharacter());
        }
    }
}
