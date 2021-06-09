package com.ardnn.mymovies.models;

import com.ardnn.mymovies.utils.Const;
import com.google.gson.annotations.SerializedName;

public class Cast {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("character")
    private String character;

    @SerializedName("profile_path")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getImageUrl(ImageSize size) {
        return Const.IMG_URL + size.getValue() + imageUrl;
    }
}
