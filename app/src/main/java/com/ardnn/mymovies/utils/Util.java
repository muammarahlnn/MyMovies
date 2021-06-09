package com.ardnn.mymovies.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ardnn.mymovies.R;

import java.util.Objects;

public class Util {
    public static boolean isSearching = false;

    @SuppressLint("StaticFieldLeak")
    private static Toolbar toolbar;

    @SuppressLint("StaticFieldLeak")
    private static TextView tvTitle;

    @SuppressLint("StaticFieldLeak")
    private static ImageView ivIcon;

    public static String convertToDate(String date) {
        String[] months = {"",
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };
        String[] splittedDate = date.split("-"); // [year, month, day]
        String year = splittedDate[0];
        String month = months[Integer.parseInt(splittedDate[1])];
        String day = splittedDate[2];

        if (day.charAt(0) == '0') {
            day = day.substring(1);
        }
        return day + " " + month + " " + year;
    }

    public static void initializeActionBar(Activity activity) {
        toolbar = activity.findViewById(R.id.toolbar_main);
        tvTitle = activity.findViewById(R.id.tv_title_toolbar);
        ivIcon = activity.findViewById(R.id.iv_icon_toolbar);
    }

    public static void setActionBar(Activity activity, String title, int icon) {
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        tvTitle.setText(title);
        ivIcon.setBackgroundResource(icon);
    }
}

