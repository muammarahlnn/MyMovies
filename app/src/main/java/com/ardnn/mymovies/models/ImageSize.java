package com.ardnn.mymovies.models;

public enum ImageSize {
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W200("w200"),
    W342("w342"),
    W500("w500"),
    W780("w780"),
    ORI("original");

    private final String size;
    ImageSize(String size) {
        this.size = size;
    }

    public String getValue() {
        return size;
    }
}
