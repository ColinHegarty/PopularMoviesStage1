package com.example.android.popularmoviesstage1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmoviesstage1.data.FavouriteMovieContract.MovieEntry;

/**
 * Created by colin.hegarty on 03/08/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favouriteMovies.db";

    private static final int DATABASE_VERSION = 1;

    private static final String DROP_IF_EXISTS = "DROP TABLE IF EXISTS ";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_RATING + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_SYNOPSIS + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_BACKDROP + " TEXT NOT NULL, " +
                "); ";

        db.execSQL(SQL_CREATE_FAVOURITE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_IF_EXISTS + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}