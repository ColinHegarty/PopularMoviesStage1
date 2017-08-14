package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstage1.data.FavouriteMovieContract;
import com.example.android.popularmoviesstage1.data.MovieDBHelper;

import java.io.IOException;
import java.net.URL;

import static android.R.attr.data;

/*
    App code has been created using both Udacity course and StackOverflow
 */
public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<Movie[]>{
    private TextView defaultTextView;
    private Movie[] listOfMovies;

    private RecyclerView moviePosterView;
    private MovieAdapter movieAdapter;

    private SQLiteDatabase favouriteDatabse;

    private String errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultTextView = (TextView) findViewById(R.id.defaultTextViw);
        defaultTextView.setText(R.string.welcome);
        moviePosterView = (RecyclerView)  findViewById(R.id.rv_movieposter);
        GridLayoutManager layoutManager;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(this, 3);
        }else{
            layoutManager = new GridLayoutManager(this, 4);
        }

        moviePosterView.setLayoutManager(layoutManager);
        moviePosterView.setHasFixedSize(true);

        networkUtilsOperations(getString(R.string.popular_query));
        Toast.makeText(this, getString(R.string.toast_popular),
                Toast.LENGTH_SHORT).show();

        MovieDBHelper movieDBHelper = new MovieDBHelper(this);
        favouriteDatabse = movieDBHelper.getWritableDatabase();

        if(!(errorMessage == null)){
            defaultTextView.setText(errorMessage);
        }


        //TODO REQUIREMENT Movies should be displayed in the main layout once the app starts, device orientation changes etc. DONE
    }

    public void networkUtilsOperations(String query){
        //TODO-2 SUGGESTION Consider checking if you have a data connection before starting this whole process DONE
        if(!NetworkUtils.isNetworkAvailable(getApplicationContext())){
            errorMessage = getString(R.string.network_error);
            return;
        }else if(getString(R.string.api_key).equals(getString(R.string.empty))){
            errorMessage = getString(R.string.api_key_empty_error);
            return;
        }

        //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
//        URL resultUrl = NetworkUtils.buildUrl(getString(R.string.connection_string) + query
//                + getString(R.string.api_connector) + getString(R.string.api_key));
//        new MovieDatabaseQuery().execute(resultUrl);

        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.search), query);

        LoaderManager lm = getSupportLoaderManager();
        android.support.v4.content.Loader movieLoader = lm.getLoader(22);

        if (movieLoader == null) {
            lm.initLoader(22, bundle, this);
        } else {
            lm.restartLoader(22, bundle, this);
        }
    }

    private Cursor getAllFavouriteMovies(){
        return favouriteDatabse.query(
                FavouriteMovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavouriteMovieContract.MovieEntry.COLUMN_MOVIE_POSTER
        );
    }



    public void createRecyclerView(){
        movieAdapter = new MovieAdapter(listOfMovies, this);
        moviePosterView.setAdapter(movieAdapter);
        defaultTextView.setVisibility(View.INVISIBLE);
        moviePosterView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.popular:
                                Toast.makeText(this, getResources().getString(R.string.toast_popular),
                                        Toast.LENGTH_SHORT).show();
                                networkUtilsOperations(getString(R.string.popular_query));
                                break;
            //TODO REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
            case R.id.top_rated:
                                Toast.makeText(this, getResources().getString(R.string.toast_top),
                                        Toast.LENGTH_SHORT).show();
                                networkUtilsOperations(getString(R.string.top_rated_query));
                                break;
            case R.id.favourite_movies:
                Toast.makeText(this, getResources().getString(R.string.favourite_menu),
                        Toast.LENGTH_SHORT).show();
                getAllFavouriteMovies();
                break;
        }
        //TODO REQUIREMENT Follow the Java Coding & Styling guidelines. DONE
        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Movie movie = listOfMovies[clickedItemIndex];
        String toastMessage = movie.getTitle();
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        Class destination = MovieDetails.class;
        Intent intentToStartAct = new Intent(this, destination);
        Bundle extras = new Bundle();
        extras.putString(getString(R.string.bundle_backdrop), movie.getBackdropPath());
        extras.putString(getString(R.string.bundle_synopsis),movie.getSynopsis());
        extras.putString(getString(R.string.bundle_title), movie.getTitle());
        extras.putString(getString(R.string.bundle_release_date), movie.getReleaseDate());
        extras.putString(getString(R.string.bundle_rating), movie.getVoteAverage());
        extras.putString(getString(R.string.bundle_id), movie.getId());

        intentToStartAct.putExtras(extras);
        startActivity(intentToStartAct);
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Movie[]>(this) {


            @Override
            protected void onStartLoading() {

                if (args == null) {
                    return;
                }

                forceLoad();
            }


            @Override
            public Movie[] loadInBackground() {
                String url =  getString(R.string.connection_string)+ args.getString(getString(R.string.search))
                        + getString(R.string.api_connector) + getString(R.string.api_key);
                URL searchUrl = NetworkUtils.buildUrl(url);
                String JSONResults = null;
                try {
                    JSONResults = NetworkUtils.getResponseFromHttpURL(searchUrl, getApplicationContext());
                } catch (IOException e) {
                    errorMessage = getString(R.string.json_error);
                }

                if (JSONResults != null && !JSONResults.equals(getResources().getString(R.string.empty))) {
                    //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
                    listOfMovies = JSONUtilities.jsonParser(JSONResults, getApplicationContext());
                    //TODO-2 AWESOME You're now doing this heavy-lifting in the background thread
                }
                return listOfMovies;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] movies) {
        createRecyclerView();
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {
        //Do nothing
    }

  /*  @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            // COMPLETED (5) Override onStartLoading
            @Override
            protected void onStartLoading() {

                // COMPLETED (6) If args is null, return.
                *//* If no arguments were passed, we don't have a query to perform. Simply return. *//*
                if (args == null) {
                    return;
                }


                // COMPLETED (8) Force a load
                forceLoad();
            }

            // COMPLETED (9) Override loadInBackground
            @Override
            public String loadInBackground() {

                URL searchUrl = NetworkUtils.buildUrl(args.getString(getString(R.string.search)));
                String JSONResults = null;
                try{
                    JSONResults = NetworkUtils.getResponseFromHttpURL(searchUrl, getApplicationContext());
                }catch(IOException e){
                    errorMessage = getString(R.string.json_error);
                }

                if(JSONResults != null && !JSONResults.equals(getResources().getString(R.string.empty))){
                    //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
                    listOfMovies = JSONUtilities.jsonParser(JSONResults, getApplicationContext());
                    //TODO-2 AWESOME You're now doing this heavy-lifting in the background thread
                }

                return JSONResults;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        createRecyclerView();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        //Do nothing
    }
*/


  /*  public class MovieDatabaseQuery extends AsyncTask<URL, Void, String>{
        //TODO SUGGESTION Consider using AsyncTaskLoader

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String JSONResults = null;
            try{
                JSONResults = NetworkUtils.getResponseFromHttpURL(searchUrl, getApplicationContext());
            }catch(IOException e){
                errorMessage = getString(R.string.json_error);
            }

            if(JSONResults != null && !JSONResults.equals(getResources().getString(R.string.empty))){
                //TODO-2 REQUIREMENT Move string literals to strings.xml or use constants as appropriate DONE
                listOfMovies = JSONUtilities.jsonParser(JSONResults, getApplicationContext());
                //TODO-2 AWESOME You're now doing this heavy-lifting in the background thread
            }

            return JSONResults;
            //TODO AWESOME You're doing network operations on a background thread.
        }

        @Override
        protected void onPostExecute(String JSONResults){
            //TODO SUGGESTION Consider moving this into doInBackground rather than doing it on your UI thread DONE

            createRecyclerView();
            //TODO-2 SUGGESTION Check you have data to display (e.g. no network, server down, parsing error) and display a suitable message to the user DONE
        }
    }*/
}
