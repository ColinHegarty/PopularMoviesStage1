package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();
    private ImageView imageView;
    private TextView textViewSynopsis;
    private TextView textViewRating;
    private TextView textViewTitle;
    private TextView textViewReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageView = (ImageView) findViewById(R.id.imageView);
        textViewSynopsis= (TextView) findViewById(R.id.synopsis);
        textViewRating= (TextView) findViewById(R.id.average_rating);
        textViewTitle= (TextView) findViewById(R.id.title);
        textViewReleaseDate= (TextView) findViewById(R.id.release_date);

        Intent intentThatStarted = getIntent();

        if (intentThatStarted != null) {
            Bundle movie = intentThatStarted.getExtras();
            textViewTitle.setText(getString(R.string.movie_title) + movie.getString(getString(R.string.bundle_title)));
            textViewTitle.setTypeface(null, Typeface.BOLD_ITALIC);
            textViewSynopsis.setText(getString(R.string.movie_synopsis)+ movie.getString(getString(R.string.bundle_synopsis)));
            textViewRating.setText(getString(R.string.movie_rating) + movie.getString(getString(R.string.bundle_rating)));
            textViewReleaseDate.setText(getString(R.string.movie_release_date)+movie.getString(getString(R.string.bundle_release_date)));
            Picasso.with(this).load(getString(R.string.poster_prefix)+ movie.getString(getString(R.string.bundle_backdrop))).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
