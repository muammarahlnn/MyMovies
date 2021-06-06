package com.ardnn.mymovies.utils;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ardnn.mymovies.R;

public class Util {
    public static boolean isSearching = false;

    public static String convertToDate(String date) {
        String[] months = {"",
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };
        String[] splittedDate = date.split("-"); // [year, month, date]
        return splittedDate[2] + " " + months[Integer.parseInt(splittedDate[1])] + ", " + splittedDate[0];
    }

    public static void setActionBar(Activity activity, String title, int icon) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar_main);
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        TextView tvTitle = activity.findViewById(R.id.tv_title_toolbar);
        tvTitle.setText(title);

        ImageView ivIcon = activity.findViewById(R.id.iv_icon_toolbar);
        ivIcon.setBackgroundResource(icon);
    }
}

