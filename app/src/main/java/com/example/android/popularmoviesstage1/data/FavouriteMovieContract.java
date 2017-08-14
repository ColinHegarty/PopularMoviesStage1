package com.example.android.popularmoviesstage1.data;

import android.provider.BaseColumns;

/**
 * Created by colin.hegarty on 31/07/2017.
 */

public class FavouriteMovieContract {

    public static final class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "favouriteMovies";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_RATING = "movieRating";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_SYNOPSIS = "movieSynopsis";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_BACKDROP = "movieBackdrop";
        public static final String COLUMN_MOVIE_POSTER = "moviePoster";

    }
}
