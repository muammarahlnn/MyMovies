package com.ardnn.mymovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ardnn.mymovies.database.daos.FavoritedDao;
import com.ardnn.mymovies.database.entities.FavoritedMovie;
import com.ardnn.mymovies.database.entities.FavoritedTvShow;

@Database(entities = {FavoritedMovie.class, FavoritedTvShow.class}, version = 2, exportSchema = false)
public abstract class FavoritedDatabase extends RoomDatabase {
    public abstract FavoritedDao favoritedDao();

    private static FavoritedDatabase INSTANCE;
    public synchronized static FavoritedDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FavoritedDatabase.class, "db_favorited")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
