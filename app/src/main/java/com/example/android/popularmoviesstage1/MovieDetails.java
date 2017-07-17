package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MovieDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private static final String TAG = MovieDetails.class.getSimpleName();
    private ImageView imageView;
    private TextView textViewSynopsis;
    private TextView textViewRating;
    private TextView textViewTitle;
    private TextView textViewReleaseDate;
    private String errorMessage;
    private String[] reviews;
    private RecyclerView reviewView;
    private ReviewAdapter reviewAdapter;
    private Bundle movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageView = (ImageView) findViewById(R.id.imageView);
        textViewSynopsis= (TextView) findViewById(R.id.synopsis);
        textViewRating= (TextView) findViewById(R.id.average_rating);
        textViewTitle= (TextView) findViewById(R.id.title);
        textViewReleaseDate= (TextView) findViewById(R.id.release_date);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reviewView = (RecyclerView)  findViewById(R.id.rv_review);
        reviewView.setLayoutManager(layoutManager);
        reviewView.setHasFixedSize(true);

        Intent intentThatStarted = getIntent();



        if (intentThatStarted != null) {
            movie = intentThatStarted.getExtras();
            textViewTitle.setText(getString(R.string.movie_title) + movie.getString(getString(R.string.bundle_title)));
            textViewTitle.setTypeface(null, Typeface.BOLD_ITALIC);
            textViewSynopsis.setText(getString(R.string.movie_synopsis)+ movie.getString(getString(R.string.bundle_synopsis)));
            textViewRating.setText(getString(R.string.movie_rating) + movie.getString(getString(R.string.bundle_rating)));
            textViewReleaseDate.setText(getString(R.string.movie_release_date)+movie.getString(getString(R.string.bundle_release_date)));
            Picasso.with(this).load(getString(R.string.poster_prefix)+ movie.getString(getString(R.string.bundle_backdrop))).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        setUpAdapter(getString(R.string.movie_review));
    }

    private void setUpAdapter(String query){
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.search), query);
        bundle.putString("author", "author");
        bundle.putString("review", "review");

        LoaderManager lm = getSupportLoaderManager();
        android.support.v4.content.Loader movieLoader = lm.getLoader(22);

        if (movieLoader == null) {
            lm.initLoader(22, bundle, this);
        } else {
            lm.restartLoader(22, bundle, this);
        }
    }

    private void setReviews(){
        reviewAdapter = new ReviewAdapter(reviews);
        reviewView.setAdapter(reviewAdapter);
    }

    @Override
    public Loader onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String url =  getString(R.string.connection_string)+ args.getString(
                        getString(R.string.search))
                        + getString(R.string.api_connector) + getString(R.string.api_key);
                URL searchUrl = NetworkUtils.buildUrl(url);
                String JSONResults = null;
                try {
                    JSONResults = NetworkUtils.getResponseFromHttpURL(searchUrl,
                            getApplicationContext());
                } catch (IOException e) {
                    errorMessage = getString(R.string.json_error);
                }

                if (JSONResults != null && !JSONResults.equals(getResources().getString(R.string.empty))) {
                    reviews = JSONUtilities.jsonParserReviews(JSONResults, getApplicationContext(),
                            args.getString(getString(R.string.json_author)),
                            args.getString(getString(R.string.json_review)));
                }
                return reviews[0];
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        setReviews();
    }

    @Override
    public void onLoaderReset(Loader loader) {
        //Do nothing
    }
}
