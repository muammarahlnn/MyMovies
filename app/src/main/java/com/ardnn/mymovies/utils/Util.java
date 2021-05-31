package com.ardnn.mymovies.utils;

import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

public class Util {
    public static void changeActionBarTitle(AppCompatActivity activity, String title) {
        activity.getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#F3CE13\">" + title + "</font>")));
    }

    public static String convertToDate(String date) {
        String[] months = {"",
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };
        String[] splittedDate = date.split("-"); // [year, month, date]

        return splittedDate[2] + " " + months[Integer.parseInt(splittedDate[1])] + ", " + splittedDate[0];
    }
}

