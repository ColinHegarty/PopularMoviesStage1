package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private static final String TAG = MovieDetails.class.getSimpleName();
    private ImageView imageView;
    private TextView textViewSynopsis;
    private TextView textViewRating;
    private TextView textViewTitle;
    private TextView textViewReleaseDate;
    private TextView textViewReviewHeading;
    private TextView textViewTitleHeading;
    private TextView textViewRatingHeading;
    private TextView textViewReleaseDateHeading;
    private TextView textViewSynopsisHeading;
    private TextView textViewTrailersHeading;
    private String errorMessage;
    private ArrayList<String> reviews;
    private ArrayList<String> trailers;
    private RecyclerView reviewView;
    private RecyclerView trailerView;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private Bundle movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        setUpDetails();
//        setUpHeadings();




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
            textViewReviewHeading.setText(getString(R.string.movie_reviews));
            textViewTrailersHeading.setText(getString(R.string.movie_trailers));
        }

        getReviews(getString(R.string.json_reviews));
        getTrailers(getString(R.string.json_trailers));

    }

    private void setUpDetails(){
        imageView = (ImageView) findViewById(R.id.imageView);
        textViewSynopsis= (TextView) findViewById(R.id.synopsis);
        textViewRating= (TextView) findViewById(R.id.average_rating);
        textViewTitle= (TextView) findViewById(R.id.title);
        textViewReleaseDate= (TextView) findViewById(R.id.release_date);
        textViewTrailersHeading = (TextView) findViewById(R.id.title_trailers);
        textViewReviewHeading = (TextView) findViewById(R.id.title_review);
    }

/*    private void setUpHeadings(){
        textViewReviewHeading = (TextView) findViewById(R.id.title_review);
        textViewTitleHeading= (TextView) findViewById(R.id.title_title);
        textViewRatingHeading= (TextView) findViewById(R.id.title_rating);
        textViewReleaseDateHeading= (TextView) findViewById(R.id.title_release_date);
        textViewSynopsisHeading= (TextView) findViewById(R.id.title_synopsis);
        textViewTrailersHeading = (TextView) findViewById(R.id.title_trailers);
//        setHeadings();
    }

    private void setHeadings(){
        textViewTitleHeading.setText(getString(R.string.movie_title));
        textViewReviewHeading.setText(getString(R.string.movie_reviews));
        textViewRatingHeading.setText(getString(R.string.movie_rating));
        textViewReleaseDateHeading.setText(getString(R.string.movie_release_date));
        textViewSynopsisHeading.setText(getString(R.string.movie_synopsis));
        textViewTrailersHeading.setText(getString(R.string.movie_trailers));
    }*/

    private void getReviews(String query){
        reviewView = (RecyclerView)  findViewById(R.id.rv_review);
        reviewView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reviewView.setHasFixedSize(true);

        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.search), query);
        bundle.putString(getString(R.string.json_author), getString(R.string.json_author));
        bundle.putString(getString(R.string.json_review), getString(R.string.json_review));

        LoaderManager lm = getSupportLoaderManager();
        android.support.v4.content.Loader movieLoader = lm.getLoader(22);

        if (movieLoader == null) {
            lm.initLoader(22, bundle, this);
        } else {
            lm.restartLoader(22, bundle, this);
        }
    }

    private void getTrailers(String query){
        trailerView = (RecyclerView) findViewById(R.id.rv_trailer);
        trailerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        trailerView.setHasFixedSize(true);

        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.search), query);
        bundle.putString(getString(R.string.json_trailers), getString(R.string.json_trailers));

        LoaderManager lm = getSupportLoaderManager();
        android.support.v4.content.Loader<ArrayList<String>> movieLoader = lm.getLoader(23);

        if (movieLoader == null) {
            lm.initLoader(23, bundle, this);
        } else {
            lm.restartLoader(23, bundle, this);
        }
    }

    private void setReviews(){
        reviewAdapter = new ReviewAdapter(reviews);
        reviewView.setAdapter(reviewAdapter);
        textViewReviewHeading.setText(getString(R.string.movie_reviews));
    }

    private void setTrailers(){
//        Log.e("setTrailers", trailers.size() + "");
        trailerAdapter = new TrailerAdapter(trailers);
        trailerView.setAdapter(trailerAdapter);
        textViewTrailersHeading.setText(getString(R.string.movie_trailers));
    }

    @Override
    public Loader onCreateLoader(int id, final Bundle args) {
        if(id == 22){
            return new AsyncTaskLoader<ArrayList<String>>(this) {
                @Override
                protected void onStartLoading() {
                    if (args == null) {
                        return;
                    }
                    forceLoad();
                }

                @Override
                public ArrayList<String> loadInBackground() {
                    String url =  getString(R.string.connection_string)+ movie.getString(getString(R.string.bundle_id)) +"/"
                            + args.getString(
                            getString(R.string.search)) + getString(R.string.api_connector) +
                            getString(R.string.api_key) ;

                    Log.e("Connection String:",getString(R.string.connection_string)+ movie.getString(getString(R.string.bundle_id)) +"/"
                            + args.getString(
                            getString(R.string.search)) + getString(R.string.api_connector) +
                            getString(R.string.api_key));

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
                    return reviews;
                }
            };
        }else if(id == 23){
            return new AsyncTaskLoader<ArrayList<String>>(this) {
                @Override
                protected void onStartLoading() {
                    if (args == null) {
                        return;
                    }
                    forceLoad();
                }

                @Override
                public ArrayList<String> loadInBackground() {
                    String url =  getString(R.string.connection_string)+ movie.getString(getString(R.string.bundle_id)) +"/"
                            + args.getString(
                            getString(R.string.search)) + getString(R.string.api_connector) +
                            getString(R.string.api_key) ;

                    Log.e("Connection String:",getString(R.string.connection_string)+ movie.getString(getString(R.string.bundle_id)) +"/"
                            + args.getString(
                            getString(R.string.search)) + getString(R.string.api_connector) +
                            getString(R.string.api_key));

                    URL searchUrl = NetworkUtils.buildUrl(url);
                    String JSONResults = null;
                    try {
                        JSONResults = NetworkUtils.getResponseFromHttpURL(searchUrl,
                                getApplicationContext());
                    } catch (IOException e) {
                        errorMessage = getString(R.string.json_error);
                    }

                    if (JSONResults != null && !JSONResults.equals(getResources().getString(R.string.empty))) {
                        trailers = JSONUtilities.jsonParserTrailers(JSONResults, getApplicationContext(),
                                args.getString(getString(R.string.json_trailers)));
                    }
//                    Log.e("trailers size", trailers.size()+"");
                    return trailers;
                }
            };
        }else {
            errorMessage = getString(R.string.json_error);
            return null;
        }

    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(loader.getId() == 22){
            setReviews();
        }else if(loader.getId() == 23){
            setTrailers();
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {
        //Do nothing
    }
}
